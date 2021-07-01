package hera.core.commands;

import com.google.common.util.concurrent.AtomicDouble;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.Reaction;
import discord4j.core.object.reaction.ReactionEmoji;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Vote {

	private static final List<String> VOTE_EMOJIS = Arrays.asList("\uD83D\uDC4D\uD83C\uDFFB", "\uD83D\uDC4E\uD83C\uDFFB", "\uD83D\uDED1");

	public static final HashMap<Long, Long> ACTIVE_VOTE_MESSAGE_IDS = new HashMap<>();

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		String voteMessage = String.join(" ", params);

		Localisation title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOTE_START_TITLE, guild);
		Localisation footer = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOTE_START_FOOTER, guild);

		return MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
				messageSpec.setTitle(String.format(title.getValue(), member.getDisplayName()));
				messageSpec.setDescription(voteMessage);
				messageSpec.setFooter(String.format(footer.getValue(), member.getDisplayName(), VOTE_EMOJIS.get(2)), null);
			}))
			.doOnNext(m -> ACTIVE_VOTE_MESSAGE_IDS.put(m.getId().asLong(), member.getId().asLong()))
			.flatMap(m -> Flux.fromIterable(VOTE_EMOJIS)
					.flatMap(emoji -> m.addReaction(ReactionEmoji.unicode(emoji)))
					.next()
			).then();
	}

	// TODO: We have a potential bug here. If someone deletes a vote message, we won't remove the message ID from the ACTIVE_VOTE_MESSAGE_IDS map.
	// So techincally someone could fill up this list with IDs and fill up our memory :/
	public static Mono<Void> executeFromReaction(ReactionAddEvent event, MessageChannel channel, Message message, String messageContent, String unicode, Member member, Guild guild) {
		if (unicode.equals(VOTE_EMOJIS.get(2)) &&
			ACTIVE_VOTE_MESSAGE_IDS.containsKey(event.getMessageId().asLong()) && // do I get a nullpointer when accessing a non existing HashMap field?
			ACTIVE_VOTE_MESSAGE_IDS.get(event.getMessageId().asLong()).equals(member.getId().asLong())) {

			// kind of abusing the fact that Objects can be final and I can still change their properties here
			// If we use normal doubles we can't use them later in the lambda
			final AtomicDouble forIt = new AtomicDouble(0);
			final AtomicDouble againstIt = new AtomicDouble(0);

			for (Reaction reaction : message.getReactions()) {
				if (reaction.getEmoji().asUnicodeEmoji().isPresent()) {
					String currentReaction = reaction.getEmoji().asUnicodeEmoji().get().getRaw();
					if (currentReaction.equals(VOTE_EMOJIS.get(0))) {
						forIt.set(reaction.getCount() - 1); // subtracting one because of Heras initial vote
					} else if (currentReaction.equals(VOTE_EMOJIS.get(1))) {
						againstIt.set(reaction.getCount() - 1); // subtracting one because of Heras initial vote
					}
				}
			}

			final double allVotes = forIt.get() + againstIt.get();
			// calculate percentage and round to 2 decimal places
			final double percentageForIt = (double) Math.round((forIt.get() / allVotes * 100) * 100) / 100;
			final double percentageAgainstIt = (double) Math.round((againstIt.get() / allVotes * 100) * 100) / 100;

			ACTIVE_VOTE_MESSAGE_IDS.remove(event.getMessageId().asLong());

			Localisation title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOTE_END_TITLE, guild);
			Localisation desc = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOTE_END_DESC, guild);

			return MessageHandler.edit(message, MessageSpec.getDefaultSpec(messageSpec -> {
				messageSpec.setTitle(title.getValue());
				messageSpec.setDescription(String.format(desc.getValue(), messageContent, (int) forIt.get(), percentageForIt, (int) againstIt.get(), percentageAgainstIt, (int) allVotes));
			}))
			.flatMap(Message::removeAllReactions)
			.then();
		}

		return Mono.empty();
	}
}
