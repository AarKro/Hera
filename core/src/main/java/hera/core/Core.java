package hera.core;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
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
import java.util.Map;
import java.util.Optional;

import static hera.metrics.MetricsLogger.STATS;
import static hera.store.DataStore.STORE;

public class Core {
	private static final Logger LOG = LoggerFactory.getLogger(Core.class);

	private static final Map<CommandName, Command> commands = new HashMap<>();

	public static void main(String[] args) {
		LOG.info("Starting Hera...");

		STORE.initialize();

		LOG.info("Get discord login token from store");
		Token loginToken = STORE.tokens().forKey(TokenKey.DISCORD_LOGIN).get(0);
		if (loginToken == null) {
			LOG.error("No discord login token found in store");
			LOG.error("Terminating startup procedure");
			return;
		}

		LOG.info("Creating command mappings");
		commands.put(CommandName.UPTIME, Uptime::execute);

		final DiscordClient client = new DiscordClientBuilder(loginToken.getToken()).build();

		// Main event stream. Commands are triggered here
		client.getEventDispatcher().on(MessageCreateEvent.class)
				.flatMap(event -> event.getGuild()
						.flatMap(guild -> Flux.fromIterable(STORE.guildSettings().forGuildAndKey(guild.getId().asLong(), GuildSettingKey.COMMAND_PREFIX))
								.flatMap(settings -> Mono.justOrEmpty(settings.getValue()))
								.defaultIfEmpty("$")
								.flatMap(commandPrefix -> Mono.justOrEmpty(event.getMessage().getContent())
										.filter(content -> content.startsWith(commandPrefix))
										.flatMap(content -> Flux.fromIterable(HeraUtil.getCommandsFromMessage(content, commandPrefix))
												.flatMap(command -> {
													// log commands call
													event.getMember()
															.flatMap(member -> Optional.of(member.getId().asLong()))
															.flatMap(memberId -> {
																STATS.logCallCount(command.getId(), guild.getId().asLong(), memberId);
																return Optional.empty();
															});

													// execute commands
													return commands.get(command.getName()).execute(event);
												})
												.next()
										)
								)
								.next()
						)
				)
				.subscribe();

		LOG.info("...Hera is ready to use as soon as connection to gateway is established");

		// login
		client.login().block();
	}
}
