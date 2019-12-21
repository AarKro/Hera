package hera.core.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.ArrayList;
import java.util.List;

public class TrackScheduler extends AudioEventAdapter {

	private List<AudioTrack> queue;

	private int queueIndex;

	private boolean loopQueue;

	private long currentQueueMessageId;

	TrackScheduler() {
		this.queue = new ArrayList<>();
		this.queueIndex = 0;
		this.loopQueue = false;
		this.currentQueueMessageId = 0;
	}

	void queue(AudioPlayer player, AudioTrack track) {
		queue.add(track);

		// immediately play track if no other track is playing
		if (player.getPlayingTrack() == null) {
			queueIndex = queue.size() - 1;
			player.playTrack(track);
		}
	}

	public void skip(AudioPlayer player) {
		// simulate the track ending
		onTrackEnd(player, player.getPlayingTrack(), AudioTrackEndReason.FINISHED);
	}

	public void clearQueue() {
		queueIndex = 0;
		queue = new ArrayList<>();
	}

	@Override
	public void onPlayerPause(AudioPlayer player) {
		// Player was paused
	}

	@Override
	public void onPlayerResume(AudioPlayer player) {
		// Player was resumed
	}

	@Override
	public void onTrackStart(AudioPlayer player, AudioTrack track) {
		// A track started playing
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if (endReason.mayStartNext) {
			if (queue.size() > queueIndex) {
				queue.set(queueIndex, queue.get(queueIndex).makeClone());
			}

			if (queue.size() > queueIndex + 1) {
				queueIndex++;
				player.playTrack(queue.get(queueIndex));
			} else if (loopQueue && queue.size() > 0) {
				queueIndex = 0;
				player.playTrack(queue.get(queueIndex));
			}
		}

		// endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
		// endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
		// endReason == STOPPED: The player was stopped.
		// endReason == REPLACED: Another track started playing while this had not finished
		// endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
		//                       clone of this back to your queue
	}

	@Override
	public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
		// An already playing track threw an exception (track end event will still be received separately)
	}

	@Override
	public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
		// Audio track has been unable to provide us any audio, might want to just start a new track
	}

	public List<AudioTrack> getQueue() {
		return queue;
	}

	public int getQueueIndex() {
		return queueIndex;
	}

	public boolean isLoopQueue() {
		return loopQueue;
	}

	public void toggleLoopQueue() {
		this.loopQueue = !this.loopQueue;
	}

	public long getCurrentQueueMessageId() {
		return currentQueueMessageId;
	}

	public void setCurrentQueueMessageId(long currentQueueMessageId) {
		this.currentQueueMessageId = currentQueueMessageId;
	}
}
