package hera.core.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import hera.database.entity.mapped.Localisation;
import hera.store.DataStore;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Uptime implements Command {
	private static final DataStore STORE = DataStore.getInstance();

	private static Uptime instance;

	private Uptime() {
	}

	public static Uptime getInstance() {
		if (instance == null) {
			instance = new Uptime();
		}

		return instance;
	}

	public Mono<Void> execute(MessageCreateEvent event) {
		return event.getMessage().getChannel().flatMap(channel -> channel.createMessage(getTime())).then();
	}

	private String getTime() {
		Long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
		Long days = uptime / 1000 / 60 / 60 / 24;
		Long hours = uptime / 1000 / 60 / 60 - days * 24;
		Long minutes = uptime / 1000 / 60 - hours * 60;
		Long seconds = uptime / 1000 - minutes * 60;

		String formatted = String.format("%sd %sh %sm %ss", days, hours, minutes, seconds);
		String[] parts = formatted.split(" ");

		StringBuilder builder = new StringBuilder();
		for (String part : parts) {
			if (builder.length() > 0 || part.charAt(0) != '0') {
				builder.append(part);
				builder.append(" ");
			}
		}

		Localisation message = STORE.localisations().forLanguageAndKey("en", "command_uptime").get(0);
		return String.format(message.getValue(), builder.toString().trim());
	}
}
