package hera.core.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
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

public class Remove {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		try {
			int trackIndex = Integer.parseInt(params.get(0));

			AudioTrack track = HeraAudioManager.getScheduler(guild).removeTrack(trackIndex);
			if (track != null) {
				Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_REMOVE, guild);
				return channel.createMessage(spec -> spec.setEmbed(embed -> {
					embed.setColor(Color.ORANGE);
					embed.setTitle(local.getValue());
					embed.setDescription(
							track.getInfo().author + " | `" + HeraUtil.getFormattedTime(track.getDuration()) + "`\n["
									+ track.getInfo().title + "](" + track.getInfo().uri + ")"
					);
				})).then();
			} else {
				Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_REMOVE_ERROR, guild);
				return channel.createMessage(spec -> spec.setEmbed(embed -> {
					embed.setColor(Color.ORANGE);
					embed.setDescription(String.format(local.getValue(), trackIndex));
				})).then();
			}
		} catch (NumberFormatException e) {
			return channel.createMessage(spec -> spec.setEmbed(embed -> {
				embed.setDescription(HeraUtil.LOCALISATION_GENERAL_ERROR.getValue());
				embed.setColor(Color.ORANGE);
			})).then();
		}
	}
}
