package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.core.music.HeraAudioManager;
import hera.database.entities.mapped.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

public class Clear {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		HeraAudioManager.getScheduler(guild).clearQueue();
		Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_CLEAR, guild);
		return channel.createMessage(spec -> spec.setEmbed(embed -> {
			embed.setColor(Color.ORANGE);
			embed.setDescription(local.getValue());
		})).then();
	}
}
