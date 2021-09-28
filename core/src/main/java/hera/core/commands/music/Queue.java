package hera.core.commands.music;

import com.sedmelluq.discord.lavaplayer.player.event.TrackStartEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.Embed;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.events.reactions.GuildReactionListener;
import hera.core.events.reactions.ReactionHandler;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.messages.formatter.TextFormatter;
import hera.core.messages.formatter.list.ListMaker;
import hera.core.messages.formatter.markdown.MarkdownHelper;
import hera.core.messages.reaction.emoji.Emoji;
import hera.core.messages.reaction.emoji.EmojiHandler;
import hera.core.music.HeraAudioManager;
import hera.core.music.TrackScheduler;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hera.core.HeraUtil.getFormattedTime;
import static hera.core.messages.formatter.markdown.Markdown.BOLD;
import static hera.core.messages.formatter.markdown.MarkdownHelper.makeItalics2;
import static hera.core.util.LocalisationUtil.getLocalisation;

//TODO wtf was i thinking redo this logic, i hate my brain
public class Queue {
	private class TrackList {
		int page, maxPage;
		List<AudioTrack> tracks;
	}

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		// show the queue starting from the page where the currently playing song is in
		int currentQueueIndex = HeraAudioManager.getScheduler(guild).getQueueIndex() ;
		int currentPageIndex = currentQueueIndex / 10;

		List<AudioTrack> tracks = HeraAudioManager.getScheduler(guild).getQueue();
		int maxPage = getMaxPage(tracks);
		List<String> emojis = getEmojis(currentPageIndex, maxPage);

		return writeMessage(currentPageIndex, channel, emojis, guild);
	}

	private static Integer getPageIndexFromQueueMessage(Message originalMessage) {
		Embed embed;
		if (originalMessage.getEmbeds().size() > 0) {
			embed = originalMessage.getEmbeds().get(0);
		} else {
			throw new RuntimeException("Queue message isn't embedded");
		}
		String footer = embed.getFooter().flatMap(f -> Optional.of(f.getText())).orElse("");
		int currentPageIndex = Integer.parseInt(footer.substring(footer.indexOf("Page: ") + 6, footer.indexOf(" of")));
		currentPageIndex--;
		return currentPageIndex;
	}

	public static Mono<Void> executeFromReaction(ReactionAddEvent event, MessageChannel messageChannel, Message originalMessage, String unicode, Member member, Guild guild, ReactionHandler.MetaData metaData) {
		int currentPageIndex = getPageIndexFromQueueMessage(originalMessage);
		List<AudioTrack> tracks = HeraAudioManager.getScheduler(guild).getQueue();
		int maxPage = getMaxPage(tracks);

		switch(unicode) {
			case Emoji.TRACK_PREVIOUS: currentPageIndex = 0; // |<<
				break;
			case Emoji.REWIND: currentPageIndex--; // <<
				break;
			case Emoji.FAST_FORWARD: currentPageIndex++; // >>
				break;
			case Emoji.TRACK_NEXT: currentPageIndex = maxPage - 1; // >>|
				break;
		}

		List<String> emojis = getEmojis(currentPageIndex, maxPage);

		return editMessageToChangePage(currentPageIndex, originalMessage, emojis, guild);
	}

	private static Mono<String[]> getQueueString(int page, Guild guild) {
		Localisation title = getLocalisation(LocalisationKey.COMMAND_QUEUE_TITLE, guild);

		TrackScheduler scheduler = HeraAudioManager.getScheduler(guild);
		List<AudioTrack> tracks = scheduler.getQueue();
		int queueIndex = scheduler.getQueueIndex();

		// gets the tracks for the queue page
		StringBuilder queueString = new StringBuilder();
		final int pageStart = page * 10;
		final int pageEnd = pageStart + 10 < tracks.size() ? pageStart + 10 : Math.max(tracks.size() - 1, pageStart);

		List<AudioTrack> pageTracks = tracks.subList(pageStart, pageEnd);

		// makes the queue List. format is : ( %index: %author \n [%title](%link) )
		ListMaker<AudioTrack> lm = new ListMaker<>(pageTracks, "%d: %s | `%s`\n[%s](%s)", (index, track) -> {
			AudioTrackInfo info = track.getInfo();
			return ListMaker.argumentMaker(index+1, info.author, getFormattedTime(track.getDuration()), info.title, info.uri);
		})
				.setLineDelimiter("\n\n")
				.addSpecialFormat((index,item) -> (index + pageStart) == queueIndex, MarkdownHelper::makeBold);
		queueString.append(lm.makeList());

		long totalDuration = 0;
		int maxPage = 1;
		if (tracks.isEmpty()) {
			Localisation local = getLocalisation(LocalisationKey.COMMAND_QUEUE_EMPTY, guild);
			queueString.append(TextFormatter.encaseWith(makeItalics2(local.getValue()), "...", " "));
		} else {
			totalDuration = tracks.stream()
					.map(AudioTrack::getDuration)
					.reduce((long) 0, Long::sum);
			maxPage = getMaxPage(tracks);
		}

		LocalisationKey loopQLocalKey;
		if (scheduler.isLoopQueue()) {
			loopQLocalKey = LocalisationKey.COMMON_ENABLED;
		} else {
			loopQLocalKey = LocalisationKey.COMMON_DISABLED;
		}

		Localisation loopQueue = getLocalisation(loopQLocalKey, guild);
		Localisation footerLocal = getLocalisation(LocalisationKey.COMMAND_QUEUE_FOOTER, guild);
		String footer = String.format(footerLocal.getValue(), page + 1, maxPage, tracks.size(), getFormattedTime(totalDuration), loopQueue.getValue());

		return Mono.just(new String[]{title.getValue(), queueString.toString(), footer});
	}

	private static List<String> getEmojis(int currentPageIndex, int maxPage) {
		List<String> emojis = new ArrayList<>();
		if (currentPageIndex > 0) {
			emojis.add(Emoji.TRACK_PREVIOUS);
			emojis.add(Emoji.REWIND);
		}
		if (currentPageIndex < maxPage - 1) {
			emojis.add(Emoji.FAST_FORWARD);
			emojis.add(Emoji.TRACK_NEXT);
		}

		return emojis;
	}

	//TODO similar to editMessage... i don't see the reason that this handels the generation of the output text
	private static Mono<Void> writeMessage(int pageIndex, MessageChannel channel, List<String> emojis, Guild guild) {
		return getQueueString(pageIndex, guild)
				.flatMap(queueStringParts -> MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
					messageSpec.setTitle(queueStringParts[0]);
					messageSpec.setDescription(queueStringParts[1]);
					messageSpec.setFooter(queueStringParts[2], null);
				}))
				.doOnSuccess(m -> GuildReactionListener.getGuildListener(guild).addListener(m.getId(), new ReactionHandler(Queue::executeFromReaction)))
						.flatMap(message -> {

								// adds a listener that changes the highlighted track whenever a new song starts. decays after 2h
								// this should technically work on more than one q method the way it is rn, may be worth changing
								TrackScheduler scheduler = HeraAudioManager.getScheduler(guild);
								scheduler.subscribeEvent(TrackStartEvent.class, e -> {
									int index = scheduler.getQueueIndex();
									return changeHighlightedTrack(message.getChannel(), message.getId(), guild, index);
								}, Duration.of(2, ChronoUnit.HOURS));

								return Mono.just(message);
				}).flatMap(message -> addReactions(message, guild, emojis)))
				.then();
	}



	//TODO make this an edit queue method, there is no reason to genereate the message content here
	private static Mono<Void> editMessageToChangePage(int pageIndex, Message editableMessage, List<String> emojis, Guild guild) {
			return editableMessage.removeAllReactions().then(getQueueString(pageIndex, guild)
				.flatMap(queueStringParts -> MessageHandler.edit(editableMessage, MessageSpec.getDefaultSpec(messageSpec -> {
					messageSpec.setTitle(queueStringParts[0]);
					messageSpec.setDescription(queueStringParts[1]);
					messageSpec.setFooter(queueStringParts[2], null);
				})).flatMap(message -> addReactions(message, guild, emojis))))
				.then();
	}

	/**
	 * This method changes the highlit track when a new track starts.
	 * It does this by first removing any existing highlights and then adding a new one on the new queueIndex
	 *
	 * @param channel The channel where the queue message is in
	 * @param messageId The message Id for the queue message
	 * @param guild The guild the message and player is in
	 * @param newIndex The current queueIndex
	 * @return empty mono
	 */
	private static Mono<Void> changeHighlightedTrack(Mono<MessageChannel> channel, Snowflake messageId, Guild guild, int newIndex) {
		return channel.flatMap(c ->
				c.getMessageById(messageId).flatMap(editableMessage -> {
					if (editableMessage.getContent().contains(getLocalisation(LocalisationKey.COMMAND_QUEUE_EMPTY, guild).getValue()))
						return Mono.empty();




					Embed embed;
					if (editableMessage.getEmbeds().size() > 0) {
						embed = editableMessage.getEmbeds().get(0);
					} else {
						throw new RuntimeException("Queue message isn't embedded");
					}

					//get the current context string
					String content = "";
					content = embed.getDescription().orElse("");
					if (content.isEmpty()) return Mono.empty();
					StringBuilder message = new StringBuilder();
					message.append(content);

					//TODO look at numbers, maybe change
					String findBoldStars = "\\*\\*[0-9]{1,10}: .{1,60} \\| `([0-9]{1,4}[dhms][ ]?){1,4}`\n\\[.{1,100}\\]\\(.{1,500}\\)\\*\\*";

					Pattern starPattern = Pattern.compile(findBoldStars);

					Matcher starMatcher = starPattern.matcher(message.toString());
					do {
						//starMatcher;
						if (starMatcher.find()) {
							int start = starMatcher.start(), end = starMatcher.end();
							message.delete(end-2, end);
							message.delete(start, start+2);
						}
						//starMatcher = starPattern.matcher(message.toString());
					} while (!starMatcher.hitEnd());

					String newHighlightNumberRegex = (newIndex + 1) + ": .{1,60} \\| `([0-9]{1,4}[dhms][ ]?){1,4}`\n\\[.{1,100}]\\(.{1,500}\\)";

					Pattern highlightLine = Pattern.compile(newHighlightNumberRegex);
					Matcher highlightLineMatcher = highlightLine.matcher(message.toString());
					highlightLineMatcher.reset();
					if (highlightLineMatcher.find()) {
						message.insert(highlightLineMatcher.end(), BOLD.getStr());
						message.insert(highlightLineMatcher.start(), BOLD.getStr());
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

	/**
	 * Adds reactions to a message
	 *
	 * @param msg The message to add emojis too
	 * @param guild The guild the message is in
	 * @param emojis the emojis which are to be set as reaction as unicode string
	 * @return a {@code Mono<Void>} to subscribe to
	 */
	private static Mono<Void> addReactions(Message msg, Guild guild, List<String> emojis) {
		HeraAudioManager.getScheduler(guild).setCurrentQueueMessageId(msg.getId().asLong());
		return EmojiHandler.addReactions(msg, emojis);
	}

	private static int getMaxPage(List<AudioTrack> tracks) {
		int maxPage = tracks.size() % 10 == 0 ? tracks.size() / 10 : tracks.size() / 10 + 1;
		return maxPage == 0 ? maxPage + 1 : maxPage;
	}
}
