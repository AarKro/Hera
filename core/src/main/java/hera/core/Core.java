package hera.core;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import hera.core.command.Command;
import hera.core.command.Uptime;
import hera.database.entity.mapped.Token;
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

	private static final Map<String, Command> commands = new HashMap<>();

	static {
		commands.put("uptime", event -> Uptime.getInstance().execute(event));
	}

	public static void main(String[] args) {
		LOG.info("Starting Hera...");

		STORE.initialize();

		LOG.info("Get discord login token from store");
		Token loginToken = STORE.tokens().forName("discord_login").get(0);
		if (loginToken == null) {
			LOG.error("No discord login token found in store");
			LOG.error("Terminating startup procedure");
			return;
		}

		final DiscordClient client = new DiscordClientBuilder(loginToken.getToken()).build();

		// Main event stream. Commands are triggered here
		client.getEventDispatcher().on(MessageCreateEvent.class)
				.flatMap(event -> event.getGuild()
						.flatMap(guild -> Flux.fromIterable(STORE.guildSettings().forGuildAndName(guild.getId().asLong(), "command_prefix"))
								.flatMap(settings -> Mono.justOrEmpty(settings.getValue()))
								.defaultIfEmpty("$")
								.flatMap(commandPrefix -> Mono.justOrEmpty(event.getMessage().getContent())
										.flatMap(content -> Flux.fromIterable(commands.entrySet())
												.filter(entry -> content.startsWith(commandPrefix + entry.getKey()))
												.flatMap(entry -> {
													// log command call
													event.getMember()
															.flatMap(member -> Optional.of(member.getId().asLong()))
															.flatMap(memberId -> {
																STATS.logCallCount(
																		STORE.commands().forName(entry.getKey()).get(0).getId(),
																		guild.getId().asLong(),
																		memberId
																);

																return Optional.empty();
															});

													// execute command
													return entry.getValue().execute(event);
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
