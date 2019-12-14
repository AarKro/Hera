package hera.core.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.core.object.entity.Guild;
import discord4j.voice.AudioProvider;
import discord4j.voice.VoiceConnection;

import java.util.HashMap;
import java.util.Map;

public class HeraAudioManager {

	private static final Map<Long, VoiceConnection> CONNECTIONS = new HashMap<>();

	private static AudioProvider provider;

	private static AudioPlayerManager playerManager;

	private static AudioPlayer player;

	private static TrackScheduler scheduler;

	HeraAudioManager() {
	}

	public static void initialise() {
		// Creates AudioPlayer instances and translates URLs to AudioTrack instances
		playerManager = new DefaultAudioPlayerManager();
		// This is an optimization strategy that Discord4J can utilize. It is not important to understand
		playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
		// Allow playerManager to parse remote sources like YouTube links
		AudioSourceManagers.registerRemoteSources(playerManager);
		// Create an AudioPlayer so Discord4J can receive audio data
		player = playerManager.createPlayer();

		provider = new HeraAudioProvider(player);

		scheduler = new TrackScheduler();

		player.addListener(scheduler);
	}

	public static HeraAudioLoadResultHandler getLoadResultHandler() {
		return new HeraAudioLoadResultHandler(player);
	}

	public static void addVC(Guild guild, VoiceConnection vc) {
		CONNECTIONS.put(guild.getId().asLong(), vc);
	}

	public static void dcFromVc(Guild guild) {
		VoiceConnection vc = getVC(guild);
		if (vc != null) {
			vc.disconnect();
			CONNECTIONS.remove(guild.getId().asLong());
		}
	}

	public static VoiceConnection getVC(Guild guild) {
		return CONNECTIONS.get(guild.getId().asLong());
	}

	public static AudioProvider getProvider() {
		return provider;
	}

	public static AudioPlayerManager getPlayerManager() {
		return playerManager;
	}

	public static AudioPlayer getPlayer() {
		return player;
	}

	public static TrackScheduler getScheduler() {
		return scheduler;
	}
}
