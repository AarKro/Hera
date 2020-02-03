package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.HeraMsgSpec;
import hera.core.messages.MessageSender;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.util.List;

public class Uptime {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return MessageSender.send(HeraMsgSpec.getDefaultSpec(channel).setDescription(getTime(guild))).then();
	}

	private static String getTime(Guild guild) {
		String time = HeraUtil.getFormattedTime(ManagementFactory.getRuntimeMXBean().getUptime());

		Localisation message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_UPTIME, guild);
		return String.format(message.getValue(), time);
	}
}
