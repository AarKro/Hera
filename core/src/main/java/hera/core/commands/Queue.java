package hera.core.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.core.music.HeraAudioManager;
import reactor.core.publisher.Mono;

import java.util.List;

public class Queue {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return getQueueString().flatMap(channel::createMessage).then();
	}

	private static Mono<String> getQueueString() {
		StringBuilder queueString = new StringBuilder("> __Current Queue:__\n\n");
		List<AudioTrack> tracks = HeraAudioManager.getScheduler().getQueue();
		int queueIndex = HeraAudioManager.getScheduler().getQueueIndex();

		long totalDuration = (long) 0;

		for (int i = 0; i < tracks.size(); i++) {
			if (i == queueIndex) queueString.append("**");
			queueString.append(i + 1);
			queueString.append(": ");
			queueString.append(tracks.get(i).getInfo().title);
			queueString.append(" | *");
			queueString.append(tracks.get(i).getInfo().author);
			queueString.append("* | `");
			queueString.append(HeraUtil.getFormattedTime(tracks.get(i).getDuration()));
			queueString.append("`");
			if (i == queueIndex) queueString.append("**");
			queueString.append("\n");

			totalDuration += tracks.get(0).getDuration();
		}

		if (tracks.isEmpty()) {
			queueString.append("*... looks like it's empty ...*\n");
		}

		queueString.append("\n> Total songs: `");
		queueString.append(tracks.size());
		queueString.append("` | Total duration: `");
		queueString.append(HeraUtil.getFormattedTime(totalDuration));
		queueString.append("` | Loop queue: ");
		queueString.append(HeraAudioManager.getScheduler().isLoopQueue() ? "`enabled`" : "`disabled`");

		return Mono.just(queueString.toString());
	}
}
