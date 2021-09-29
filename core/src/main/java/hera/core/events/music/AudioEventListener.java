package hera.core.events.music;

import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class AudioEventListener implements Comparable<AudioEventListener> {
	public static final Duration DEFAULT_EVENT_LIVETIME = Duration.of(12, ChronoUnit.HOURS);

	private Function<AudioEvent, Mono<Void>> eventHandler;
	private BiPredicate<AudioEvent, AudioEventListener> destructionCondition = null;
	private LocalDateTime startedAt;
	private Duration lifeTime;
	private LocalDateTime endOfLifeTime;
	private Integer hashCode = null;

	private AudioEventListener() {
		startedAt = LocalDateTime.now();
	}

	public AudioEventListener(Function<AudioEvent, Mono<Void>> eventHandler) {
		this(eventHandler, DEFAULT_EVENT_LIVETIME);
	}

	public AudioEventListener(Function<AudioEvent, Mono<Void>> eventHandler, Duration lifetime) {
		this();
		this.eventHandler = eventHandler;
		this.endOfLifeTime = startedAt.plus(lifetime);
	}

	public AudioEventListener(Function<AudioEvent, Mono<Void>> eventHandler, BiPredicate<AudioEvent, AudioEventListener> destructionCondition) {
		this(eventHandler);
		this.destructionCondition = destructionCondition;
	}

	public AudioEventListener(Function<AudioEvent, Mono<Void>> eventHandler, Duration lifetime, BiPredicate<AudioEvent, AudioEventListener> destructionCondition) {
		this(eventHandler, lifetime);
		this.destructionCondition = destructionCondition;
	}

	public Mono<Void> execute(AudioEvent event) {
		return eventHandler.apply(event);
	}

	public Mono<Void> executeIfValid(AudioEvent event) {
		if (!isValid(event)) return Mono.empty();
		return eventHandler.apply(event);
	}

	public boolean isValid(AudioEvent event) {
		boolean toDestruct = destructionCondition != null && destructionCondition.test(event, this);
		return stillInLifetime() && !toDestruct;
	}

	public boolean stillInLifetime() {
		return LocalDateTime.now().isBefore(endOfLifeTime);
	}

	public Function<AudioEvent, Mono<Void>> getEventHandler() {
		return eventHandler;
	}

	public LocalDateTime getEndOfLifeTime() {
		return endOfLifeTime;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) hashCode =  Objects.hash(eventHandler, startedAt, lifeTime);
		return hashCode;
	}

	@Override
	public int compareTo(@NotNull AudioEventListener audioEventListener) {
		if (this.hashCode == audioEventListener.hashCode) {
			return 0;
		} else {
			int start = this.startedAt.compareTo(audioEventListener.startedAt);
			if (start == 0) return this.lifeTime.compareTo(audioEventListener.lifeTime);
			return start;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;

		if (!(obj instanceof AudioEventListener)) return false;

		return this.compareTo((AudioEventListener) obj) == 0;
	}
}
