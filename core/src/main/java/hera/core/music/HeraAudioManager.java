package hera.core.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.voice.AudioProvider;
import discord4j.voice.VoiceConnection;
import hera.database.entities.GuildSetting;
import hera.database.types.GuildSettingKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hera.store.DataStore.STORE;

public class HeraAudioManager {

	private static final Map<Long, VoiceConnection> CONNECTIONS = new HashMap<>();

	private static final Map<Long, TrackScheduler> SCHEDULERS = new HashMap<>();

	private static final Map<Long, AudioPlayer> PLAYERS = new HashMap<>();

	public static AudioPlayerManager playerManager;

	private HeraAudioManager() {
	}

	public static void initialise() {
		// Creates AudioPlayer instances and translates URLs to AudioTrack instances
		playerManager = new DefaultAudioPlayerManager();
		// This is an optimization strategy that Discord4J can utilize. It is not important to understand
		playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
		// Allow playerManager to parse remote sources like YouTube links
		AudioSourceManagers.registerRemoteSources(playerManager);
	}

	private static TrackScheduler createSchedulerForGuild(Guild guild) {
		TrackScheduler scheduler = new TrackScheduler(guild);
		getPlayer(guild).addListener(scheduler);
		SCHEDULERS.put(guild.getId().asLong(), scheduler);
		return scheduler;
	}

	public static TrackScheduler getScheduler(Guild guild) {
		TrackScheduler scheduler = SCHEDULERS.get(guild.getId().asLong());
		if (scheduler == null) {
			scheduler = createSchedulerForGuild(guild);
		}
		return scheduler;
	}

	public static AudioProvider getProvider(Guild guild) {
		return new HeraAudioProvider(getPlayer(guild));
	}

	private static AudioPlayer createPlayerForGuild(Guild guild) {
		AudioPlayer player = playerManager.createPlayer();
		List<GuildSetting> guildSettings = STORE.guildSettings().forGuildAndKey(guild.getId().asLong(), GuildSettingKey.VOLUME);
		if (!guildSettings.isEmpty()) {
			Integer volume = Integer.parseInt(guildSettings.get(0).getValue());
			player.setVolume(volume);
		}
		PLAYERS.put(guild.getId().asLong(), player);
		return player;
	}

	public static AudioPlayer getPlayer(Guild guild) {
		AudioPlayer player = PLAYERS.get(guild.getId().asLong());
		if (player == null) {
			player = createPlayerForGuild(guild);
		}
		return player;
	}

	public static HeraAudioLoadResultHandler getLoadResultHandler(Guild guild, MessageChannel channel) {
		return new HeraAudioLoadResultHandler(getPlayer(guild), guild, channel);
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
}
