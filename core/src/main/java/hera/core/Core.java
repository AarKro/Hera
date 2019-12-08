package hera.core;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import hera.core.commands.Command;
import hera.core.commands.Commands;
import hera.database.entities.mapped.Guild;
import hera.database.entities.mapped.Token;
import hera.database.entities.mapped.User;
import hera.database.types.GuildSettingKey;
import hera.database.types.TokenKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.metrics.MetricsLogger.STATS;
import static hera.store.DataStore.STORE;

public class Core {
	private static final Logger LOG = LoggerFactory.getLogger(Core.class);

	public static void main(String[] args) {
		LOG.info("Starting Hera...");

		LOG.info("Get discord login token from store");
		List<Token> loginTokens = STORE.tokens().forKey(TokenKey.DISCORD_LOGIN);
		if (loginTokens.isEmpty()) {
			LOG.error("No discord login token found in store");
			LOG.error("Terminating startup procedure");
			return;
		}

		STORE.initialise();

		LOG.info("Initialising command mappings");
		Commands.initialise();

		final DiscordClient client = new DiscordClientBuilder(loginTokens.get(0).getToken()).build();

		HeraCommunicationInterface hci = new HeraCommunicationInterface(client);
		hci.startupHCI();

		// on guild join -> Add guild to store if we don't have it already
		client.getEventDispatcher().on(GuildCreateEvent.class)
				.flatMap(event -> Flux.fromIterable(STORE.guilds().getAll())
						.filter(sg -> sg.getSnowflake().equals(event.getGuild().getId().asLong()))
						.hasElements()
						.filter(guildExists -> !guildExists)
						.flatMap(guildExists -> {
							STORE.guilds().add(new Guild(event.getGuild().getId().asLong()));
							return Mono.empty();
						})
				)
				.subscribe();

		// on guild join -> Add members of guild to store if we don't have them already
		client.getEventDispatcher().on(GuildCreateEvent.class)
				.flatMap(event -> event.getGuild().getMembers()
						.flatMap(member -> Flux.fromIterable(STORE.users().getAll())
								.filter(su -> su.getSnowflake().equals(member.getId().asLong()))
								.hasElements()
								.filter(memberExists -> !memberExists)
								.flatMap(memberExists -> {
									STORE.users().add(new User(member.getId().asLong()));
									return Mono.empty();
								})
						).next()
				)
				.subscribe();

		// on user joins guild  -> Add new member to store if we don't have him already
		client.getEventDispatcher().on(MemberJoinEvent.class)
				.flatMap(event -> Flux.fromIterable(STORE.users().getAll())
						.filter(su -> su.getSnowflake().equals(event.getMember().getId().asLong()))
						.hasElements()
						.filter(memberExists -> !memberExists)
						.flatMap(memberExists -> {
							STORE.users().add(new User(event.getMember().getId().asLong()));
							return Mono.empty();
						})
				)
				.subscribe();

		// Main event stream. Commands are triggered here
		client.getEventDispatcher().on(MessageCreateEvent.class)
				.flatMap(event -> event.getGuild()
						.flatMap(guild -> Flux.fromIterable(STORE.guildSettings().forGuildAndKey(guild.getId().asLong(), GuildSettingKey.COMMAND_PREFIX))
								.flatMap(settings -> Mono.justOrEmpty(settings.getValue()))
								.defaultIfEmpty("$")
								.flatMap(commandPrefix -> Mono.justOrEmpty(event.getMessage().getContent())
										.filter(content -> content.startsWith(commandPrefix))
										.flatMap(content -> Mono.justOrEmpty(HeraUtil.getCommandFromMessage(content, commandPrefix, guild))
												.flatMap(command -> Mono.justOrEmpty(event.getMember())
														.flatMap(member -> event.getMessage().getChannel()
																.filterWhen(channel -> HeraUtil.checkPermissions(command, member, guild, channel))
																.filterWhen(channel -> HeraUtil.checkParameters(content, command, channel))
																.flatMap(channel -> Mono.just(HeraUtil.extractParameters(content, command))
																		.flatMap(params -> {
																			// log commands call
																			STATS.logCallCount(command.getId(), guild.getId().asLong(), member.getId().asLong());
																			// execute commands
																			return Commands.COMMANDS.get(command.getName()).execute(event, guild, member, channel, params);
																		})
																)
														)
												)
										)
								).next()
						)
				)
				.subscribe();

		LOG.info("...Hera is ready to use as soon as connection to gateway is established");

		// login
		client.login().block();
	}
}
