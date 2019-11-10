package hera.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import hera.enums.BoundChannel;
import hera.events.eventSupplements.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
	
	private static final Logger LOG = LoggerFactory.getLogger(TrackScheduler.class);
	
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
		LOG.debug("Start of: TrackScheduler.queue");
		// Calling startTrack with the noInterrupt set to true will start the
		// track only if nothing is currently playing. If
		// something is playing, it returns false and does nothing. In that case
		// the player was already playing so this
		// track goes to the queue instead.
		if (!player.startTrack(track, true)) {
			LOG.debug("A song is already playing, thus track goes to the queue");
			queue.offer(track);
		}
		LOG.debug("End of: TrackScheduler.queue");
	}
	
	public void queuePlaylist(AudioPlaylist playlist) {
		LOG.debug("Start of: TrackScheduler.queuePlaylist");
		List<AudioTrack> tracks = playlist.getTracks();
		if (!player.startTrack(playlist.getTracks().get(0), true)) {
			LOG.debug("A song is already playing, thus all tracks go to the queue");
			queue.addAll(tracks);
		} else {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), 
					"Now playing:", tracks.get(0).getInfo().title + " by " + tracks.get(0).getInfo().author + " | " + getFormattedTime(tracks.get(0).getDuration()));
			tracks.remove(0);
			LOG.debug("First song of playlist is now playing, rest is added to the queue");
			queue.addAll(tracks);
		}
		LOG.debug("End of: TrackScheduler.queuePlaylist");
	}

	public void nextTrack() {
		LOG.debug("Start of: TrackScheduler.nextTrack");
		// Start the next track, regardless of if something is already playing
		// or not. In case queue was empty, we are
		// giving null to startTrack, which is a valid argument and will simply
		// stop the player.
		AudioTrack track = queue.poll();
		if(track != null) {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Now playing:", track.getInfo().title + " by " + track.getInfo().author + " | " + getFormattedTime(track.getDuration()));
			player.startTrack(track, false);
		}
		LOG.debug("End of: TrackScheduler.nextTrack");
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		LOG.debug("Start of: TrackScheduler.onTrackEnd");
		// Only start the next track if the end reason is suitable for it
		// (FINISHED or LOAD_FAILED)
		if (endReason.mayStartNext) {
			if(loopQueue) addSong(track.makeClone());
			nextTrack();
		}
		LOG.debug("End of: TrackScheduler.onTrackEnd");
	}
	
	public void requeueSong() {
		LOG.debug("Start of: TrackScheduler.requeueSong");
		if(player.getPlayingTrack() != null) {
			addSong(player.getPlayingTrack().makeClone());
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Requeued:", player.getPlayingTrack().getInfo().title + " by " + player.getPlayingTrack().getInfo().author);
		} else {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "There is no song playing at the moment.");
		}
		LOG.debug("End of: TrackScheduler.requeueSong");
	}
	
	public void skipSong() {
		LOG.debug("Start of: TrackScheduler.skipSong");
		if(loopQueue && player.getPlayingTrack() != null) addSong(player.getPlayingTrack().makeClone());
		nextTrack();
		LOG.debug("End of: TrackScheduler.skipSong");
	}
	
	public void addSong(AudioTrack track) {
		LOG.debug("Start of: TrackScheduler.addSong");
		queue.offer(track);
		LOG.debug("End of: TrackScheduler.addSong");
	}
	
	public void clearQueue() {
		LOG.debug("Start of: TrackScheduler.clearQueue");
		queue.clear();
		LOG.debug("End of: TrackScheduler.clearQueue");
	}
	
	public void removeSongFromQueue(AudioTrack trackToRemove) {
		LOG.debug("Start of: TrackScheduler.removeSongFromQueue");
		queue.remove(trackToRemove);
		LOG.debug("End of: TrackScheduler.removeSongFromQueue");
	}
	
	private String getFormattedTime(long milliseconds) {
		LOG.debug("Start of: TrackScheduler.getFormattedTime");
		LOG.debug("End of: TrackScheduler.getFormattedTime");
		return String.format("%02d:%02d:%02d", (milliseconds / (1000 * 60 * 60)) % 24, (milliseconds / (1000 * 60)) % 60, (milliseconds / 1000) %60);
	}
	
	public AudioTrack[] getQueue() {
		LOG.debug("Start of: TrackScheduler.getQueue");
		LOG.debug("End of: TrackScheduler.getQueue");
		return queue.toArray(new AudioTrack[0]);
	}
	
	public void setQueueAfterMove(AudioTrack[] queue) {
		LOG.debug("Start of: TrackScheduler.setQueueAfterMove");
		clearQueue();
		this.queue.addAll(Arrays.asList(queue));
		LOG.debug("End of: TrackScheduler.setQueueAfterMove");
	}
	
	public boolean getLoopQueue() {
		return loopQueue;
	}
	
	public void setLoopQueue(boolean loopQueue) {
		this.loopQueue = loopQueue;
	}
}