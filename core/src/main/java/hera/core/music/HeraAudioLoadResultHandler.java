package hera.core.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import reactor.core.publisher.Flux;

import java.awt.*;

public final class HeraAudioLoadResultHandler implements AudioLoadResultHandler {

	private AudioPlayer player;

	private Guild guild;

	private MessageChannel channel;

	HeraAudioLoadResultHandler(AudioPlayer player, Guild guild, MessageChannel channel) {
		this.player = player;
		this.guild = guild;
		this.channel = channel;
	}

	@Override
	public void trackLoaded(AudioTrack track) {
		HeraAudioManager.getScheduler(guild).queue(player, track);
		channel.createMessage(spec -> spec.setEmbed(embed -> {
			embed.setColor(Color.ORANGE);
			embed.setTitle("Added to queue");
			embed.setDescription(
					track.getInfo().author + " | `" + HeraUtil.getFormattedTime(track.getDuration()) + "`\n["
					+ track.getInfo().title + "](" + track.getInfo().uri + ")"
			);
		}))
		.subscribe();
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		Flux.fromIterable(playlist.getTracks())
				.doOnNext(track -> HeraAudioManager.getScheduler(guild).queue(player, track))
				.map(AudioTrack::getDuration)
				.reduce(((accumulation, duration) -> accumulation + duration))
				.flatMap(totalDuration -> channel.createMessage(spec -> spec.setEmbed(embed -> {
					embed.setColor(Color.ORANGE);
					embed.setTitle("Added to queue");
					embed.setDescription(
							playlist.getName() + "\nTotal songs: `" + playlist.getTracks().size()
									+ "` | Total duration: `" + HeraUtil.getFormattedTime(totalDuration) + "`"
					);
				})))
				.subscribe();
	}

	@Override
	public void noMatches() {
		// Notify the user that we've got nothing
	}

	@Override
	public void loadFailed(FriendlyException throwable) {
		// Notify the user that everything exploded
	}
}
