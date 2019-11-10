package hera.music;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import hera.events.eventSupplements.MessageSender;
import hera.instanceManagement.SingletonInstancer;
import sx.blah.discord.handle.obj.IGuild;

public class GuildAudioPlayerManager {

	private static final Logger LOG = LoggerFactory.getLogger(GuildAudioPlayerManager.class);
	
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
		LOG.debug("Start of: GuildAudioPlayerManager.getGuildAudioPlayer");
		GuildMusicManager musicManager = musicManagers.get(guild.getLongID());

		if (musicManager == null) {
			musicManager = new GuildMusicManager(apm, ms);
			musicManagers.put(guild.getLongID(), musicManager);
		}

		guild.getAudioManager().setAudioProvider(musicManager.getAudioProvider());
		
		LOG.info("Organized music manager for guild " + guild.getName() + " : " + guild.getLongID());

		LOG.debug("End of: GuildAudioPlayerManager.getGuildAudioPlayer");
		return musicManager;
	}
}
