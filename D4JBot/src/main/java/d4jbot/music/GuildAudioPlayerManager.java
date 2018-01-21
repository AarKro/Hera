package d4jbot.music;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import d4jbot.misc.MessageSender;
import sx.blah.discord.handle.obj.IGuild;

public class GuildAudioPlayerManager {
	
	private Map<Long, GuildMusicManager> musicManagers;
	private AudioPlayerManager apm;
	private MessageSender ms;
	
	private static GuildAudioPlayerManager instance;
	
	public static GuildAudioPlayerManager getInstance(AudioPlayerManager apm, MessageSender ms) {
		if(instance == null) instance = new GuildAudioPlayerManager(apm, ms); 
		return instance;
	}
	
	// constructor
	private GuildAudioPlayerManager(AudioPlayerManager apm, MessageSender ms) {
		this.apm = apm;
		this.musicManagers = new HashMap<>();
	}
	
	public GuildMusicManager getGuildAudioPlayer(IGuild guild) {
		GuildMusicManager musicManager = musicManagers.get(guild.getLongID());

		if (musicManager == null) {
			musicManager = new GuildMusicManager(apm, ms);
			musicManagers.put(guild.getLongID(), musicManager);
		}

		guild.getAudioManager().setAudioProvider(musicManager.getAudioProvider());

		return musicManager;
	}
	
	public AudioPlayerManager getApm() {
		return this.apm;
	}
}
