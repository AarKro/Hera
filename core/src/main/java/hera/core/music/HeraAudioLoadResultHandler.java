package hera.core.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.object.entity.Guild;

public final class HeraAudioLoadResultHandler implements AudioLoadResultHandler {

	private AudioPlayer player;

	private Guild guild;

	HeraAudioLoadResultHandler(AudioPlayer player, Guild guild) {
		this.player = player;
		this.guild = guild;
	}

	@Override
	public void trackLoaded(AudioTrack track) {
		HeraAudioManager.getScheduler(guild).queue(player, track);
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		for (AudioTrack track : playlist.getTracks()) {
			HeraAudioManager.getScheduler(guild).queue(player, track);
		}
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
