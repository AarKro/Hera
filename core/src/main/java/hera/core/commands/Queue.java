package hera.core.commands;

import com.sedmelluq.discord.lavaplayer.player.event.TrackEndEvent;
import com.sedmelluq.discord.lavaplayer.player.event.TrackStartEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.Embed;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.messages.Regex;
import hera.core.messages.Regex.RegexMatch;
import hera.core.messages.formatter.DefaultStrings;
import hera.core.messages.formatter.ListGen;
import hera.core.messages.formatter.TextFormatter;
import hera.core.music.HeraAudioManager;
import hera.core.music.TrackScheduler;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import hera.database.types.SnowflakeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		TrackScheduler scheduler = HeraAudioManager.getScheduler(guild);
		List<AudioTrack> tracks = scheduler.getQueue();
		int queueIndex = scheduler.getQueueIndex();


		StringBuilder queueString = new StringBuilder();
		final int pageStart = page * 10;
		final int pageEnd = pageStart + 10 < tracks.size() ? pageStart + 10 : tracks.size() -1;

		List<AudioTrack> pageTracks = tracks.subList(pageStart, pageEnd);
		ListGen<AudioTrack> generator = new ListGen<AudioTrack>()
				.setNodes("%s: %s | `%s`\n[%s](%s)")
				.setItems(pageTracks)
				.addIndexConverter(i -> String.valueOf(i + pageStart + 1))
				.addItemConverter(t -> t.getInfo().author)
				.addItemConverter(t -> HeraUtil.getFormattedTime(t.getDuration()))
				.addItemConverter(t -> t.getInfo().title)
				.addItemConverter(t -> t.getInfo().uri)
				.addSpecialLineFormat(t -> (t.getIndex() + pageStart) == queueIndex, s -> String.format(TextFormatter.encaseWith(s, DefaultStrings.BOLD)))
				.setLineBreak("\n\n");
		queueString.append(generator.makeList());


		long totalDuration = 0;
		int maxPage = 1;
		if (tracks.isEmpty()) {
			Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_QUEUE_EMPTY, guild);
			queueString.append(TextFormatter.encaseWith(local.getValue(), DefaultStrings.ITALICS2.getStr(), "...", " "));
		} else {
			totalDuration = tracks.stream()
					.map(AudioTrack::getDuration)
					.reduce((long) 0, (accumulation, track) -> accumulation + track);

			maxPage = getMaxPage(tracks);
		}

		LocalisationKey enabledDisabled;
		if (scheduler.isLoopQueue()) {
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
		//TODO make this outsourced constants
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

	//TODO gonna add a little sleep cuz it is too fast maybe this is just my pc?
	private static Mono<Void> writeMessage(int pageIndex, MessageChannel channel, List<String> emojis, Guild guild) {
		return getQueueString(pageIndex, guild)
				.flatMap(queueStringParts -> MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
					messageSpec.setTitle(queueStringParts[0]);
					messageSpec.setDescription(queueStringParts[1]);
					messageSpec.setFooter(queueStringParts[2], null);
				})).flatMap(message -> {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					TrackScheduler scheduler = HeraAudioManager.getScheduler(guild);
					scheduler.subscribeEvent(TrackStartEvent.class, e -> {
						int index = scheduler.getQueueIndex();
						return changeHighlightedTrack(message.getChannel(), message.getId(), guild, index);
					});
					return Mono.just(message);
				}).flatMap(message -> addReactions(message, guild, emojis)))
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

	private static Mono<Void> changeHighlightedTrack(Mono<MessageChannel> channel, Snowflake messageId, Guild guild, int newIndex) {
		return channel.flatMap(c ->
				c.getMessageById(messageId).flatMap(editableMessage -> {
					if (editableMessage.getContent().contains(HeraUtil.getLocalisation(LocalisationKey.COMMAND_QUEUE_EMPTY, guild).getValue()))
						return Mono.empty();
					//TODO change this is hacky
					Embed embed = editableMessage.getEmbeds().get(0);
					String content = "";
					content = embed.getDescription().orElse("");
					if (content.isEmpty()) return Mono.empty();
					StringBuilder message = new StringBuilder();
					message.append(content);

					//TODO look at numbers, maybe change
					String findStartBoldStars = "\\*\\*(?=[0-9]{1,10}: .{1,60} \\| `([0-9]{1,4}[dhms][ ]?){1,4}`)";
					String findEndBoldStars = "(?<=\\[.{1,100}\\]\\(.{1,500}\\))\\*\\*";

					Pattern startBold = Pattern.compile(findStartBoldStars);
					Pattern endBold = Pattern.compile(findEndBoldStars);

					Matcher startMatcher;
					Matcher endMatcher;
					do {
						startMatcher = startBold.matcher(message.toString());
						startMatcher.reset();
						if (startMatcher.find()) {
							message.delete(startMatcher.start(), startMatcher.end());
						}
						endMatcher = endBold.matcher(message.toString());
						endMatcher.reset();
						if (endMatcher.find()) {
							message.delete(endMatcher.start(), endMatcher.end());
						}
						startMatcher = startBold.matcher(message.toString());
						endMatcher = endBold.matcher(message.toString());
						startMatcher.reset();
						endMatcher.reset();
					} while (startMatcher.find() || endMatcher.find());

					String newHighlightNumberRegex = (newIndex + 1) + ": .{1,60} \\| `([0-9]{1,4}[dhms][ ]?){1,4}`\\s\\[.{1,100}\\]\\(.{1,500}\\)";

					Pattern highlightLine = Pattern.compile(newHighlightNumberRegex);
					Matcher highlightLineMatcher = highlightLine.matcher(message.toString());
					highlightLineMatcher.reset();
					if (highlightLineMatcher.find()) {
						message.insert(highlightLineMatcher.start(), DefaultStrings.BOLD);

						highlightLineMatcher = highlightLine.matcher(message.toString());
						highlightLineMatcher.reset();
						highlightLineMatcher.find();
						message.insert(highlightLineMatcher.end(), DefaultStrings.BOLD);

					}

					String title = embed.getTitle().orElse("");
					String footer = embed.getFooter().flatMap(f -> Optional.of(f.getText())).orElse("");
					return MessageHandler.edit(editableMessage, MessageSpec.getDefaultSpec(messageSpec -> {
						messageSpec.setTitle(title);
						messageSpec.setDescription(message.toString());
						messageSpec.setFooter(footer, null);
					})).then();
				}));
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
