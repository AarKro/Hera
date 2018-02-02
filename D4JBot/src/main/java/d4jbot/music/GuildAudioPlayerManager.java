package d4jbot.music;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import d4jbot.events.Lq;
import d4jbot.misc.MessageSender;
import d4jbot.misc.SingletonInstancer;
import sx.blah.discord.handle.obj.IGuild;

public class GuildAudioPlayerManager {

	private static GuildAudioPlayerManager instance;
	
	public static GuildAudioPlayerManager getInstance() {
		if (instance == null) instance = new GuildAudioPlayerManager();
		return instance;
	}
	
	private Map<Long, GuildMusicManager> musicManagers;
	private AudioPlayerManager apm;
	private MessageSender ms;
	
	// constructor
	private GuildAudioPlayerManager() {
		this.ms = MessageSender.getInstance();
		this.apm = SingletonInstancer.getAPMInstance();
		this.musicManagers = new HashMap<>();
	}
	
	public AudioPlayerManager getApm() {
		return apm;
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
}
