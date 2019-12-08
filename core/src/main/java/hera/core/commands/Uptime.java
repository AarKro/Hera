package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.database.entities.mapped.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.util.List;

public class Uptime {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return channel.createMessage(getTime(guild)).then();
	}

	private static String getTime(Guild guild) {
		Long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
		Long days = uptime / 1000 / 60 / 60 / 24;
		Long hours = uptime / 1000 / 60 / 60 - (days * 24);
		Long minutes = uptime / 1000 / 60 - (hours * 60 + (days * 24 * 60));
		Long seconds = uptime / 1000 - (minutes * 60 + (hours * 60 * 60 + (days * 24 * 60 * 60)));

		String formatted = String.format("%sd %sh %sm %ss", days, hours, minutes, seconds);
		String[] parts = formatted.split(" ");

		StringBuilder builder = new StringBuilder();
		for (String part : parts) {
			if (builder.length() > 0 || part.charAt(0) != '0') {
				builder.append(part);
				builder.append(" ");
			}
		}

		Localisation message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_UPTIME, guild);
		return String.format(message.getValue(), builder.toString().trim());
	}
}
