package hera.core.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageSpec;
import hera.core.messages.MessageHandler;
import hera.core.music.HeraAudioManager;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

public class Remove {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		try {
			int trackIndex = Integer.parseInt(params.get(0)) - 1;

			AudioTrack track = HeraAudioManager.getScheduler(guild).removeTrack(trackIndex);
			if (track != null) {
				Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_REMOVE, guild);
				String desc = track.getInfo().author + " | `" + HeraUtil.getFormattedTime(track.getDuration()) + "`\n["
						+ track.getInfo().title + "](" + track.getInfo().uri + ")";

				return MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
					messageSpec.setDescription(desc);
					messageSpec.setTitle(local.getValue());
				})).then();
			} else {
				Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_REMOVE_ERROR, guild);
				return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> {
					messageSpec.setDescription(String.format(local.getValue(), trackIndex));
				})).then();
			}
		} catch (NumberFormatException e) {
			return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> {
				messageSpec.setDescription(HeraUtil.LOCALISATION_PARAM_ERROR.getValue());
			})).then();
		}
	}
}
