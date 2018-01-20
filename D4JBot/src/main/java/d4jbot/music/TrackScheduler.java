package d4jbot.music;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;

public class TrackScheduler extends AudioEventAdapter {
	private final AudioPlayer player;
	private final BlockingQueue<AudioTrack> queue;
	private boolean loopQueue;
	private MessageSender ms;

	public TrackScheduler(AudioPlayer player, MessageSender ms) {
		this.player = player;
		this.queue = new LinkedBlockingQueue<>();
		this.loopQueue = false;
		this.ms = ms;
	}

	public void queue(AudioTrack track) {
		// Calling startTrack with the noInterrupt set to true will start the
		// track only if nothing is currently playing. If
		// something is playing, it returns false and does nothing. In that case
		// the player was already playing so this
		// track goes to the queue instead.
		if (!player.startTrack(track, true)) {
			queue.offer(track);
		}
	}

	public void nextTrack() {
		// Start the next track, regardless of if something is already playing
		// or not. In case queue was empty, we are
		// giving null to startTrack, which is a valid argument and will simply
		// stop the player.
		AudioTrack track = queue.poll();
		if(track != null) ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Now playing:\n" + track.getInfo().title + " by " + track.getInfo().author + " | " + getFormattedTime(track.getDuration()));
		player.startTrack(track, false);
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		// Only start the next track if the end reason is suitable for it
		// (FINISHED or LOAD_FAILED)
		if (endReason.mayStartNext) {
			nextTrack();
			if(loopQueue) queue(track);
		}
	}
	
	private String getFormattedTime(long milliseconds) {
		return String.format("%02d:%02d:%02d", (milliseconds / (1000 * 60 * 60)) % 24, (milliseconds / (1000 * 60)) % 60, (milliseconds / 1000) %60);
	}
	
	public AudioTrack[] getQueue() {
		return queue.toArray(new AudioTrack[0]);
	}
	
	public boolean getLoopQueue() {
		return loopQueue;
	}
	
	public void setLoopQueue(boolean loopQueue) {
		this.loopQueue = loopQueue;
	}
}