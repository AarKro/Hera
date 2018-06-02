package hera.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import hera.enums.BotSettings;
import hera.eventSupplements.MessageSender;

public class GuildMusicManager {
  
	public final AudioPlayer player;
	public final TrackScheduler scheduler;
  
	public GuildMusicManager(AudioPlayerManager manager, MessageSender ms) {
		player = manager.createPlayer();
		scheduler = new TrackScheduler(player, ms);
		
		player.addListener(scheduler);
		player.setVolume(Integer.parseInt(BotSettings.BOT_VOLUME.getPropertyValue()));
	}
	
	public AudioProvider getAudioProvider() {
		return new AudioProvider(player);
	}
	
	// getters & setters
	public TrackScheduler getScheduler() {
		return scheduler;
	}
}