package hera.core;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import hera.core.command.Command;
import hera.core.command.Uptime;
import hera.database.entity.mapped.Token;
import hera.store.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Core {
	private static final Logger LOG = LoggerFactory.getLogger(Core.class);

	private static final DataStore STORE = DataStore.getInstance();

	private static final Map<String, Command> commands = new HashMap<>();

	static {
		commands.put("uptime", event -> Uptime.getInstance().execute(event));
	}

	public static void main(String[] args) {
		LOG.info("Starting Hera...");

		STORE.initialize();

		LOG.info("Get discord login token from store");
		Token loginToken = STORE.tokens().getAll().stream().filter((t) -> t.getName().equals("discord_login")).collect(Collectors.toList()).get(0);
		if (loginToken == null) {
			LOG.error("No discord login token found in store");
			LOG.error("Terminating startup procedure");
			return;
		}

		final DiscordClient client = new DiscordClientBuilder(loginToken.getToken()).build();

		// Main event stream. Commands are triggered here
		client.getEventDispatcher().on(MessageCreateEvent.class)
				.flatMap(event -> event.getGuild()
						.flatMap(guild -> Flux.fromIterable(STORE.guildSettings().getAll())
								.filter(setting -> setting.getGuild() == guild.getId().asLong() && setting.getName().equals("command_prefix"))
								.flatMap(settings -> Mono.justOrEmpty(settings.getValue()))
								.defaultIfEmpty("$")
								.flatMap(commandPrefix -> Mono.justOrEmpty(event.getMessage().getContent())
										.flatMap(content -> Flux.fromIterable(commands.entrySet())
												.filter(entry -> content.startsWith(commandPrefix + entry.getKey()))
												.flatMap(entry -> entry.getValue().execute(event))
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
