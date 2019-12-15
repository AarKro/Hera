package hera.core.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import hera.core.HeraUtil;
import hera.core.music.HeraAudioManager;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Queue {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		// show the queue starting from the page where the currently playing song is in
		int currentQueueIndex = HeraAudioManager.getScheduler().getQueueIndex() + 1;
		int currentPageIndex = currentQueueIndex % 10 == 0 ? currentQueueIndex / 10 : currentQueueIndex / 10 + 1;
		currentPageIndex = currentPageIndex == 0 ? currentPageIndex : currentPageIndex - 1;

		List<AudioTrack> tracks = HeraAudioManager.getScheduler().getQueue();
		int maxPage = getMaxPage(tracks);
		List<String> emojis = getEmojis(currentPageIndex, maxPage);

		return writeMessage(currentPageIndex, channel, emojis);
	}

	public static Mono<Void> executeFromReaction(ReactionAddEvent event, MessageChannel channel, String message, String unicode) {
		int currentPageIndex = Integer.parseInt(message.substring(message.indexOf("Page: ") + 6, message.indexOf(" of")));
		currentPageIndex--;
		List<AudioTrack> tracks = HeraAudioManager.getScheduler().getQueue();
		int maxPage = getMaxPage(tracks);

		switch(unicode) {
			case "\u23EE": currentPageIndex = 0; // |<<
				break;
			case "\u23EA": currentPageIndex--; // <<
				break;
			case "\u23E9": currentPageIndex++; // >>
				break;
			case "\u23ED": currentPageIndex = maxPage - 1; // >>|
				break;
		}

		List<String> emojis = getEmojis(currentPageIndex, maxPage);

		return writeMessage(currentPageIndex, channel, emojis);
	}

	private static Mono<String[]> getQueueString(int page) {
		String title = "Current Queue";

		List<AudioTrack> tracks = HeraAudioManager.getScheduler().getQueue();
		int queueIndex = HeraAudioManager.getScheduler().getQueueIndex();

		StringBuilder queueString = new StringBuilder();
		for (int i = (10 * page); i < tracks.size() && i < (10 * page + 10); i++) {
			if (i == queueIndex) queueString.append("**");
			queueString.append(i + 1);
			queueString.append(": ");
			queueString.append(tracks.get(i).getInfo().author);
			queueString.append(" | `");
			queueString.append(HeraUtil.getFormattedTime(tracks.get(i).getDuration()));
			queueString.append("`\n[");
			queueString.append(tracks.get(i).getInfo().title);
			queueString.append("](");
			queueString.append(tracks.get(i).getInfo().uri);
			queueString.append(")");
			if (i == queueIndex) queueString.append("**");
			queueString.append("\n\n");
		}

		long totalDuration = (long) 0;
		int maxPage = 1;
		if (tracks.isEmpty()) {
			queueString.append("*... looks like it's empty ...*");
		} else {
			totalDuration = tracks.stream()
					.map(AudioTrack::getDuration)
					.reduce((long) 0, (accumulation, track) -> accumulation + track);

			maxPage = getMaxPage(tracks);
		}

		StringBuilder footer = new StringBuilder();
		footer.append("Page: ");
		footer.append(page + 1);
		footer.append(" of ");
		footer.append(maxPage);
		footer.append(" | Total songs: ");
		footer.append(tracks.size());
		footer.append(" | Total duration: ");
		footer.append(HeraUtil.getFormattedTime(totalDuration));
		footer.append(" | Loop queue: ");
		footer.append(HeraAudioManager.getScheduler().isLoopQueue() ? "enabled" : "disabled");

		return Mono.just(new String[]{title, queueString.toString(), footer.toString()});
	}

	private static List<String> getEmojis(int currentPageIndex, int maxPage) {
		List<String> emojis = new ArrayList<>();
		if (currentPageIndex > 0) {
			emojis.add("\u23EE");
			emojis.add("\u23EA");
		}
		if (currentPageIndex < maxPage - 1) {
			emojis.add("\u23E9");
			emojis.add("\u23ED");
		}

		return emojis;
	}

	private static Mono<Void> writeMessage(int pageIndex, MessageChannel channel, List<String> emojis) {
		return getQueueString(pageIndex)
				.flatMap(queueStringParts -> channel.createMessage(spec -> spec.setEmbed(embed -> {
							embed.setColor(Color.ORANGE);
							embed.setTitle(queueStringParts[0]);
							embed.setDescription(queueStringParts[1]);
							embed.setFooter(queueStringParts[2], null);
						}))
						.doOnNext(message -> HeraAudioManager.getScheduler().setCurrentQueueMessageId(message.getId().asLong()))
						.flatMap(m -> Flux.fromIterable(emojis)
								.flatMap(emoji -> m.addReaction(ReactionEmoji.unicode(emoji)))
								.next()
						)
				)
				.then();
	}

	private static int getMaxPage(List<AudioTrack> tracks) {
		int maxPage = tracks.size() % 10 == 0 ? tracks.size() / 10 : tracks.size() / 10 + 1;
		return maxPage == 0 ? maxPage + 1 : maxPage;
	}
}
