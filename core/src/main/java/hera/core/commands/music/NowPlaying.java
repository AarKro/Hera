package hera.core.commands.music;

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

public class NowPlaying {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return getNowPlayingString(guild).flatMap(nowPlayingStringParts -> MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
			messageSpec.setTitle(nowPlayingStringParts[0]);
			messageSpec.setDescription(nowPlayingStringParts[1]);
		}))).then();
	}

	private static Mono<String[]> getNowPlayingString(Guild guild) {
		Localisation title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_NOWPLAYING_TITLE, guild);
		AudioTrack track = HeraAudioManager.getPlayer(guild).getPlayingTrack();
		StringBuilder nowPlayingString = new StringBuilder();
		String message;
		if (track != null) {
			nowPlayingString.append(track.getInfo().author);
			nowPlayingString.append("\n[");
			nowPlayingString.append(track.getInfo().title);
			nowPlayingString.append("](");
			nowPlayingString.append(track.getInfo().uri);
			nowPlayingString.append(")\n\n`");
			nowPlayingString.append(HeraUtil.getFormattedTime(track.getPosition()));
			nowPlayingString.append("` **|**`");

			long tenPercent = track.getDuration() / 10;
			int percentagePosition = (int) (track.getPosition() / tenPercent);

			for (int i = 0; i < 10; i++) {
				if (i == percentagePosition) nowPlayingString.append("»»");
				else nowPlayingString.append("--");
			}

			nowPlayingString.append("`**|** `");
			nowPlayingString.append(HeraUtil.getFormattedTime(track.getDuration()));
			nowPlayingString.append("`");

			Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_NOWPLAYING, guild);
			message = String.format(local.getValue(), nowPlayingString.toString());
		} else {
			message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_NOWPLAYING_NO_SONG, guild).getValue();
		}

		return Mono.just(new String[] {title.getValue(), message});
	}
}
