package hera.core;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.gateway.json.dispatch.MessageCreate;
import hera.core.commands.Command;
import hera.core.commands.Uptime;
import hera.database.entities.mapped.Token;
import hera.database.types.CommandName;
import hera.database.types.GuildSettingKey;
import hera.database.types.TokenKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hera.metrics.MetricsLogger.STATS;
import static hera.store.DataStore.STORE;

public class Core {
	private static final Logger LOG = LoggerFactory.getLogger(Core.class);

	private static final Map<CommandName, Command> commands = new HashMap<>();

	public static void main(String[] args) {
		LOG.info("Starting Hera...");

		STORE.initialize();

		LOG.info("Get discord login token from store");
		List<Token> loginTokens = STORE.tokens().forKey(TokenKey.DISCORD_LOGIN);
		if (loginTokens.isEmpty()) {
			LOG.error("No discord login token found in store");
			LOG.error("Terminating startup procedure");
			return;
		}

		LOG.info("Creating command mappings");
		commands.put(CommandName.UPTIME, Uptime::execute);

		final DiscordClient client = new DiscordClientBuilder(loginTokens.get(0).getToken()).build();

		DiscordChannelConnector dcc = new DiscordChannelConnector(client);
		dcc.startupConnector();

		client.getEventDispatcher().on(MessageCreateEvent.class)
				.flatMap(event -> event.getGuild()
						.flatMap(guild -> event.getMessage().getChannel()
								.filter(channel -> {
									if (dcc.getActiveChannel() != null) return channel.getId().asLong() == dcc.getActiveChannel().getId().asLong();
									else return false;
								})
								.flatMap(channel -> Mono.justOrEmpty(event.getMessage().getContent())
										.flatMap(content -> Mono.justOrEmpty(event.getMember())
												.doOnNext(member -> dcc.updateMessageDisplay(member, content))
										)
								)

						)
				).subscribe();

		// Main event stream. Commands are triggered here
		client.getEventDispatcher().on(MessageCreateEvent.class)
				.flatMap(event -> event.getGuild()
						.flatMap(guild -> Flux.fromIterable(STORE.guildSettings().forGuildAndKey(guild.getId().asLong(), GuildSettingKey.COMMAND_PREFIX))
								.flatMap(settings -> Mono.justOrEmpty(settings.getValue()))
								.defaultIfEmpty("$")
								.flatMap(commandPrefix -> Mono.justOrEmpty(event.getMessage().getContent())
										.filter(content -> content.startsWith(commandPrefix))
										.flatMap(content -> Mono.justOrEmpty(HeraUtil.getCommandFromMessage(content, commandPrefix))
												.flatMap(command -> Mono.justOrEmpty(event.getMember())
														.flatMap(member -> event.getMessage().getChannel()
																.filterWhen(channel -> HeraUtil.checkPermissions(command, member, guild, channel))
																.filterWhen(channel -> HeraUtil.checkParameters(content, command, channel))
																.flatMap(channel -> Mono.just(HeraUtil.extractParameters(content, command))
																		.flatMap(params -> {
																			// log commands call
																			STATS.logCallCount(command.getId(), guild.getId().asLong(), member.getId().asLong());
																			// execute commands
																			return commands.get(command.getName()).execute(event, guild, member, channel, params);
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
