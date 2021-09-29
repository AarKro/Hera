package hera.core.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.events.music.AudioEventListener;
import hera.core.events.music.AudioEventListeners;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.*;
import hera.database.types.BindingName;
import hera.database.types.ConfigFlagName;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static hera.store.DataStore.STORE;

public class TrackScheduler extends AudioEventAdapter {

	private List<AudioTrack> queue;

	private int queueIndex;

	private boolean loopQueue;

	private long currentQueueMessageId;

	private Guild guild;

	HashMap<Class<? extends AudioEvent>, AudioEventListeners> eventListeners = new HashMap<>();

	TrackScheduler(Guild guild) {
		this.queue = new ArrayList<>();
		this.queueIndex = 0;
		this.loopQueue = false;
		this.currentQueueMessageId = 0;
		this.guild = guild;
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
  
	public AudioTrack removeTrack(int trackIndex) {
		if (trackIndex >= 0 && trackIndex < queue.size()) {
			// we need to set back the queue index by 1 if the removed track already played,
			// because else we'd skip a song
			if (trackIndex <= queueIndex && queueIndex != 0) {
				queueIndex--;
			}

			return queue.remove(trackIndex);
    }

		return null;
	}
  
	public AudioTrack moveTrack(int trackIndex, int destination) {
		if (trackIndex >= 0 && trackIndex < queue.size() && destination >= 0 && destination < queue.size() && trackIndex != destination && trackIndex != queueIndex) {
			if (trackIndex <= queueIndex && destination > queueIndex) {
				queueIndex--;
			} else if (trackIndex > queueIndex && destination <= queueIndex) {
				queueIndex++;
			}

			AudioTrack toMove =  queue.remove(trackIndex);
			queue.add(destination, toMove);

			return toMove;
    }

		return null;
	}
  
	public AudioTrack jumpTo(int trackIndex, AudioPlayer player) {
		if (trackIndex >= 0 && trackIndex < queue.size() && trackIndex != queueIndex) {
			queue.set(queueIndex, queue.get(queueIndex).makeClone());

			queueIndex = trackIndex;
			player.playTrack(queue.get(queueIndex));

			return queue.get(queueIndex);
		}

		return null;
	}

	public void shuffle(AudioPlayer player) {
		Collections.shuffle(queue);
		queueIndex = 0;

		AudioTrack currentTrack = player.getPlayingTrack();
		if (currentTrack != null) {
			int newIndex = queue.indexOf(currentTrack);
			if (newIndex > -1) {
				AudioTrack track = queue.remove(newIndex);
				queue.add(0, track);
			}
		}
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
		List<BindingType> bType = STORE.bindingTypes().forName(BindingName.MUSIC);
		List<Binding> bindings = STORE.bindings().forGuildAndType(guild.getId().asLong(), bType.get(0));
		// Only announce when there is a music channel binding
		if (!bindings.isEmpty()) {
			List<ConfigFlagType> type = STORE.configFlagTypes().forName(ConfigFlagName.ANNOUNCE_NEXT_SONG);
			List<ConfigFlag> flags = STORE.configFlags().forGuildAndType(guild.getId().asLong(), type.get(0));
			// get channel from snowflake
			if (!flags.isEmpty() && flags.get(0).getValue()) {
				guild.getChannelById(Snowflake.of(bindings.get(0).getSnowflake())).flatMap(channel -> {
					return MessageHandler.send((MessageChannel) channel, MessageSpec.getDefaultSpec(spec -> {
						Localisation local = HeraUtil.getLocalisation(LocalisationKey.CONFIG_FLAG_ANNOUNCE_NEXT_SONG, guild);
						spec.setTitle(local.getValue());
						String descString = track.getInfo().author + " | `" + HeraUtil.getFormattedTime(track.getDuration()) + "`\n[" + track.getInfo().title + "](" + track.getInfo().uri + ")";
						spec.setDescription(descString);
					}));
				}).subscribe();
			}
		}
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if (endReason.mayStartNext) {
			if (queue.size() > queueIndex && queueIndex > -1) {
				queue.set(queueIndex, queue.get(queueIndex).makeClone());
			}

			if (queue.size() > queueIndex + 1) {
				queueIndex++;
				player.playTrack(queue.get(queueIndex));
			} else if (loopQueue && queue.size() > 0) {
				queueIndex = 0;
				player.playTrack(queue.get(queueIndex));
			} else {
				// You get here by trying to skip the last song in the queue when loop queue is disabled
				// In this case we just want to stop the track, but not pause the player
				player.stopTrack();
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

	@Override
	public void onEvent(AudioEvent event) {
		super.onEvent(event);

		if (!eventListeners.isEmpty() && eventListeners.containsKey(event.getClass())) {
			//remove all listeners that became invalid since last time, execute all the remaining ones
			eventListeners.get(event.getClass()).executeValidRemoveInvalid(event);
		}
	}

	//TODO check which of these methods make sense, destructionCondition ones can prob be removed and should be handled by this one by construction the listener prior to applying it.
	public AudioEventListener subscribeEvent(Class<? extends AudioEvent> event, AudioEventListener listener) {
		if (eventListeners.containsKey(event)) {
			eventListeners.get(event).add(listener);
		} else {
			eventListeners.put(event, new AudioEventListeners(listener));
		}
		return listener;
	}

	public AudioEventListener subscribeEvent(Class<? extends AudioEvent> event, Function<AudioEvent, Mono<Void>> handler) {
		return subscribeEvent(event, new AudioEventListener(handler));
	}

	public AudioEventListener subscribeEvent(Class<? extends AudioEvent> event, Function<AudioEvent, Mono<Void>> handler, Duration lifetime) {
		return subscribeEvent(event, new AudioEventListener(handler, lifetime));
	}

	public AudioEventListener subscribeEvent(Class<? extends AudioEvent> event, Function<AudioEvent, Mono<Void>> handler, BiPredicate<AudioEvent, AudioEventListener> destructionCondition) {
		return subscribeEvent(event, new AudioEventListener(handler, destructionCondition));
	}

	public AudioEventListener subscribeEvent(Class<? extends AudioEvent> event, Function<AudioEvent, Mono<Void>> handler, Duration lifetime, BiPredicate<AudioEvent, AudioEventListener> destructionCondition) {
		return subscribeEvent(event, new AudioEventListener(handler, lifetime, destructionCondition));
	}

	public boolean unsubscribe(Class<? extends  AudioEvent> event, AudioEventListener listener) {
		if (eventListeners.containsKey(event)) {
			eventListeners.get(event).remove(listener);
			return true;
		}
		return false;
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
