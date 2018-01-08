package d4jbot.misc;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import d4jbot.enums.BoundChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.audio.AudioPlayer;

public class AudioQueueManager {

	private AudioPlayer ap;
	private MessageSender ms;

	// default constructor
	public AudioQueueManager() { }
	
	// constructor
	public AudioQueueManager(IGuild iGuild, MessageSender ms) {
		this.ap = new AudioPlayer(iGuild);
		this.ms = ms;
	}

	public void play(URL url) {
		try {
			ap.queue(url);
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Song queued!");
		} catch (IOException e) {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Sorry, but something went wrong :/");
			System.out.println("IOException");
		} catch (UnsupportedAudioFileException e) {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Sorry, but something went wrong :/");
			System.out.println("UnsupportedAudioFileException");
		}
	}
	
	public void queue () {
		String queue = "";
		for(int i = 0; i < ap.getPlaylistSize(); i++) {
			queue += "Song " + i + "\n" + ap.getPlaylist().get(i).getMetadata().get("url") + "\n\n";
		}
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, queue);
	}
	
	public void loopQueue() {
		ap.setLoop(!ap.isLooping());
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Queue loop " + (ap.isLooping() ? "enabled" : "disabled") + ".");
	}
	
	// getters & setters
	public AudioPlayer getAp() {
		return ap;
	}

	public void setAp(AudioPlayer ap) {
		this.ap = ap;
	}
}
