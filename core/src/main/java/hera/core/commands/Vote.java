package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.reaction.Reaction;
import discord4j.core.object.reaction.ReactionEmoji;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

public class Vote {

	private static final List<String> VOTE_EMOJIS = Arrays.asList("\u1F44D", "\u1F44E", "\u1F6D1");

	public static final HashMap<Long, Long> ACTIVE_VOTE_MESSAGE_IDS = new HashMap<>();

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		String voteMessage = String.join(" ", params);
		ACTIVE_VOTE_MESSAGE_IDS.put(member.getId().asLong(), event.getMessage().getId().asLong());
		/*
		return MessageSender.send(new HeraMsgSpec(channel) {{
			setTitle("Vote started");
			setDescription(voteMessage);
			setFooter(member.getMention() + ", react with " + VOTE_EMOJIS.get(2) + " to end the vote");
		}}).flatMap(m -> Flux.fromIterable(VOTE_EMOJIS)
				.flatMap(emoji -> m.addReaction(ReactionEmoji.unicode(emoji)))
				.next()
		).then();
		*/
		return Mono.empty();
	}

	// TODO: We have a potential bug where. If someone deletes a vote message, we won't remove the message ID from the ACTIVE_VOTE_MESSAGE_IDS map.
	// So techincally someone could fill up this list with IDs and fill up our memory :/
	public static Mono<Void> executeFromReaction(ReactionAddEvent event, MessageChannel channel, Set<Reaction> reactions, String message, String unicode, Member member) {
		if (unicode.equals(VOTE_EMOJIS.get(2)) &&
			ACTIVE_VOTE_MESSAGE_IDS.containsKey(member.getId().asLong()) && // do I get a nullpointer when accessing a non existing HashMap field?
			ACTIVE_VOTE_MESSAGE_IDS.get(member.getId().asLong()).equals(event.getMessageId().asLong())) {

			double forIt = 0;
			double againstIt = 0;

			for (Reaction reaction : reactions) {
				if (reaction.getEmoji().asUnicodeEmoji().isPresent()) {
					String currentReaction = reaction.getEmoji().asUnicodeEmoji().toString();
					if (currentReaction.equals(VOTE_EMOJIS.get(0))) {
						forIt = reaction.getCount();
					} else if (currentReaction.equals(VOTE_EMOJIS.get(1))) {
						againstIt = reaction.getCount();
					}
				}
			}

			double allVotes = forIt + againstIt;
			// calculate percentage and round to 2 decimal places
			double percentageForIt = (double) Math.round((forIt / allVotes * 100) * 100) / 100;
			double percentageAgainstIt = (double) Math.round((againstIt / allVotes * 100) * 100) / 100;

			ACTIVE_VOTE_MESSAGE_IDS.remove(member.getId().asLong());

			/*
			return MessageSender.send(new HeraMsgSpec(channel) {{
				setTitle("Vote ended");
				setDescription(message + "\n\nYes: " + percentageForIt + "%\nNo:" + percentageAgainstIt + "%");
			}}).then();
			*/
		}

		return Mono.empty();
	}
}
