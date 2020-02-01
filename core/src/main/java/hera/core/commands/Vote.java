package hera.core.commands;

import com.google.common.util.concurrent.AtomicDouble;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.reaction.Reaction;
import discord4j.core.object.reaction.ReactionEmoji;
import hera.core.messages.HeraMsgSpec;
import hera.core.messages.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Vote {

	private static final Logger LOG = LoggerFactory.getLogger(Vote.class);

	private static final List<String> VOTE_EMOJIS = Arrays.asList("\uD83D\uDC4D\uD83C\uDFFB", "\uD83D\uDC4E\uD83C\uDFFB", "\uD83D\uDED1");

	public static final HashMap<Long, Long> ACTIVE_VOTE_MESSAGE_IDS = new HashMap<>();

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		String voteMessage = String.join(" ", params);

		return MessageSender.send(new HeraMsgSpec(channel) {{
			setTitle(member.getDisplayName() + " started a vote");
			setDescription(voteMessage);
			setFooter(member.getDisplayName() + ", react with " + VOTE_EMOJIS.get(2) + " to end the vote", null);
		}}).doOnNext(m -> ACTIVE_VOTE_MESSAGE_IDS.put(member.getId().asLong(), m.getId().asLong()))
			.flatMap(m -> Flux.fromIterable(VOTE_EMOJIS)
			.flatMap(emoji -> m.addReaction(ReactionEmoji.unicode(emoji)))
			.next()
		).then();
	}

	// TODO: We have a potential bug where. If someone deletes a vote message, we won't remove the message ID from the ACTIVE_VOTE_MESSAGE_IDS map.
	// So techincally someone could fill up this list with IDs and fill up our memory :/
	public static Mono<Void> executeFromReaction(ReactionAddEvent event, MessageChannel channel, Set<Reaction> reactions, String message, String unicode, Member member) {
		if (unicode.equals(VOTE_EMOJIS.get(2)) &&
			ACTIVE_VOTE_MESSAGE_IDS.containsKey(member.getId().asLong()) && // do I get a nullpointer when accessing a non existing HashMap field?
			ACTIVE_VOTE_MESSAGE_IDS.get(member.getId().asLong()).equals(event.getMessageId().asLong())) {

			// kind of abusing the fact that Objects can be final and I can still change their properties here
			// If we use normal doubles we can't use them later in the lambda
			final AtomicDouble forIt = new AtomicDouble(0);
			final AtomicDouble againstIt = new AtomicDouble(0);

			for (Reaction reaction : reactions) {
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
			double percentageForIt = (double) Math.round((forIt.get() / allVotes * 100) * 100) / 100;
			double percentageAgainstIt = (double) Math.round((againstIt.get() / allVotes * 100) * 100) / 100;

			ACTIVE_VOTE_MESSAGE_IDS.remove(member.getId().asLong());

			return MessageSender.send(new HeraMsgSpec(channel) {{
				setTitle(member.getDisplayName() + "s vote ended");
				setDescription("> " + message + "\n\nNumber of votes: " + (int) allVotes + "\n\nYes: " + (int) forIt.get() + " | " + percentageForIt + "%\nNo: " + (int) againstIt.get() + " | " + percentageAgainstIt + "%");
			}}).then();
		}

		return Mono.empty();
	}
}
