package hera.core.events.music;

import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;

import java.util.*;
import java.util.function.Consumer;

public class AudioEventListeners extends ArrayList<AudioEventListener> {

	public AudioEventListeners(AudioEventListener listener) {
		super();
		add(listener);
	}

	public void removeEndedLifetime() {
		this.removeIf(l -> !l.stillInLifetime());
	}

	public void removeInValid(AudioEvent event) {
		this.removeIf(l -> !l.isValid(event));
	}

	public void forEachInLifetime(Consumer<? super AudioEventListener> action) {
		super.forEach(l -> { if (l.stillInLifetime()) action.accept(l);});
	}

	public void executeAll(AudioEvent event) {
		super.forEach(l -> l.execute(event).subscribe());
	}

	public void executeValid(AudioEvent event) {
		super.forEach(l -> l.executeIfValid(event).subscribe());
	}

	//goes through the list and
	public void executeValidRemoveInvalid(AudioEvent event) {
		int pointer = 0;
		while (pointer < this.size()) {
			if (!this.get(pointer).isValid(event)) {
				this.remove(pointer);
				System.err.println("REMOVED LISTENER");
			} else {
				this.get(pointer).execute(event).subscribe();
				pointer++;
			}
		}
	}

}
