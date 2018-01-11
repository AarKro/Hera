package d4jbot.misc;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import sx.blah.discord.handle.obj.IGuild;

public class GuildAudioPlayerManager {
	
	private Map<Long, GuildMusicManager> musicManagers;
	private AudioPlayerManager apm;
	
	// constructor
	public GuildAudioPlayerManager(AudioPlayerManager apm) {
		this.apm = apm;
		this.musicManagers = new HashMap<>();
	}
	
	public GuildMusicManager getGuildAudioPlayer(IGuild guild) {
		GuildMusicManager musicManager = musicManagers.get(guild.getLongID());

		if (musicManager == null) {
			musicManager = new GuildMusicManager(apm);
			musicManagers.put(guild.getLongID(), musicManager);
		}

		guild.getAudioManager().setAudioProvider(musicManager.getAudioProvider());

		return musicManager;
	}
}
