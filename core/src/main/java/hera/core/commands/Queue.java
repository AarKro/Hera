package hera.core.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.music.HeraAudioManager;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Queue {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		// show the queue starting from the page where the currently playing song is in
		int currentQueueIndex = HeraAudioManager.getScheduler(guild).getQueueIndex() ;
		int currentPageIndex = currentQueueIndex / 10;

		List<AudioTrack> tracks = HeraAudioManager.getScheduler(guild).getQueue();
		int maxPage = getMaxPage(tracks);
		List<String> emojis = getEmojis(currentPageIndex, maxPage);

		return writeMessage(currentPageIndex, channel, emojis, guild);
	}

	public static Mono<Void> executeFromReaction(ReactionAddEvent event, Message originalMessage, String message, String unicode, Guild guild) {
		int currentPageIndex = Integer.parseInt(message.substring(message.indexOf("Page: ") + 6, message.indexOf(" of")));
		currentPageIndex--;
		List<AudioTrack> tracks = HeraAudioManager.getScheduler(guild).getQueue();
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

		return editMessage(currentPageIndex, originalMessage, emojis, guild);
	}

	private static Mono<String[]> getQueueString(int page, Guild guild) {
		Localisation title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_QUEUE_TITLE, guild);

		List<AudioTrack> tracks = HeraAudioManager.getScheduler(guild).getQueue();
		int queueIndex = HeraAudioManager.getScheduler(guild).getQueueIndex();

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

		long totalDuration = 0;
		int maxPage = 1;
		if (tracks.isEmpty()) {
			Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_QUEUE_EMPTY, guild);
			queueString.append("*... ");
			queueString.append(local.getValue());
			queueString.append(" ...*");
		} else {
			totalDuration = tracks.stream()
					.map(AudioTrack::getDuration)
					.reduce((long) 0, (accumulation, track) -> accumulation + track);

			maxPage = getMaxPage(tracks);
		}

		LocalisationKey enabledDisabled;
		if (HeraAudioManager.getScheduler(guild).isLoopQueue()) {
			enabledDisabled = LocalisationKey.COMMON_ENABLED;
		} else {
			enabledDisabled = LocalisationKey.COMMON_DISABLED;
		}

		Localisation loopQueue = HeraUtil.getLocalisation(enabledDisabled, guild);
		Localisation footerLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_QUEUE_FOOTER, guild);
		String footer = String.format(footerLocal.getValue(), page + 1, maxPage, tracks.size(), HeraUtil.getFormattedTime(totalDuration), loopQueue.getValue());

		return Mono.just(new String[]{title.getValue(), queueString.toString(), footer});
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

	private static Mono<Void> writeMessage(int pageIndex, MessageChannel channel, List<String> emojis, Guild guild) {
		return getQueueString(pageIndex, guild)
				.flatMap(queueStringParts -> MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
					messageSpec.setTitle(queueStringParts[0]);
					messageSpec.setDescription(queueStringParts[1]);
					messageSpec.setFooter(queueStringParts[2], null);
				})).flatMap(message -> addReactions(message, guild, emojis)))
				.then();
	}

	private static Mono<Void> editMessage(int pageIndex, Message editableMessage, List<String> emojis, Guild guild) {
		return editableMessage.removeAllReactions().then(getQueueString(pageIndex, guild)
				.flatMap(queueStringParts -> MessageHandler.edit(editableMessage, MessageSpec.getDefaultSpec(messageSpec -> {
					messageSpec.setTitle(queueStringParts[0]);
					messageSpec.setDescription(queueStringParts[1]);
					messageSpec.setFooter(queueStringParts[2], null);
				})).flatMap(message -> addReactions(message, guild, emojis))))
				.then();
	}

	private static Mono<Void> addReactions(Message msg, Guild guild, List<String> emojis) {
		HeraAudioManager.getScheduler(guild).setCurrentQueueMessageId(msg.getId().asLong());
		return Flux.fromIterable(emojis)
					.flatMap(emoji -> msg.addReaction(ReactionEmoji.unicode(emoji)))
					.next();
	}

	private static int getMaxPage(List<AudioTrack> tracks) {
		int maxPage = tracks.size() % 10 == 0 ? tracks.size() / 10 : tracks.size() / 10 + 1;
		return maxPage == 0 ? maxPage + 1 : maxPage;
	}
}
