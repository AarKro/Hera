package hera.core.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.database.entities.mapped.Localisation;
import hera.database.types.LocalisationKey;
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
		Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_PLAY_TITLE, guild);

		HeraAudioManager.getScheduler(guild).queue(player, track);
		channel.createMessage(spec -> spec.setEmbed(embed -> {
			embed.setColor(Color.ORANGE);
			embed.setTitle(local.getValue());
			embed.setDescription(
					track.getInfo().author + " | `" + HeraUtil.getFormattedTime(track.getDuration()) + "`\n["
					+ track.getInfo().title + "](" + track.getInfo().uri + ")"
			);
		}))
		.subscribe();
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		Localisation title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_PLAY_TITLE, guild);
		Localisation desc = HeraUtil.getLocalisation(LocalisationKey.PLAYLIST_LOADED, guild);

		Flux.fromIterable(playlist.getTracks())
				.doOnNext(track -> HeraAudioManager.getScheduler(guild).queue(player, track))
				.map(AudioTrack::getDuration)
				.reduce(((accumulation, duration) -> accumulation + duration))
				.flatMap(totalDuration -> channel.createMessage(spec -> spec.setEmbed(embed -> {
					embed.setColor(Color.ORANGE);
					embed.setTitle(title.getValue());
					embed.setDescription(
							playlist.getName() + "\n" +
							String.format(desc.getValue(), "`"+playlist.getTracks().size()+"`", "`"+HeraUtil.getFormattedTime(totalDuration)+"`")
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
