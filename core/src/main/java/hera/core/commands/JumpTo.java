package hera.core.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.music.HeraAudioManager;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

public class JumpTo {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		try {
			int trackIndex = Integer.parseInt(params.get(0)) - 1;

			AudioTrack track = HeraAudioManager.getScheduler(guild).jumpTo(trackIndex, HeraAudioManager.getPlayer(guild));
			if (track != null) {
				Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_JUMPTO, guild);
				String message = track.getInfo().author + " | `" + HeraUtil.getFormattedTime(track.getDuration()) + "`\n["
						+ track.getInfo().title + "](" + track.getInfo().uri + ")";
				return MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec ->{
					messageSpec.setDescription(message);
					messageSpec.setTitle(String.format(local.getValue(), trackIndex + 1));
				})).then();
			} else {
				Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_JUMPTO_ERROR, guild);
				return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> {
					messageSpec.setDescription(String.format(local.getValue(), trackIndex + 1));
				})).then();
			}
		} catch (NumberFormatException e) {
			return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> {
				messageSpec.setDescription(HeraUtil.LOCALISATION_PARAM_ERROR.getValue());
			})).then();
		}
	}
}
