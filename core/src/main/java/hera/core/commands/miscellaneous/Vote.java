package hera.core.commands.miscellaneous;

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
import hera.core.events.reactions.GuildReactionListener;
import hera.core.events.reactions.ReactionHandler;
import hera.core.messages.reaction.emoji.Emoji;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

public class Vote {

	private static final List<String> VOTE_EMOJIS = Arrays.asList(Emoji.THUMBS_UP, Emoji.THUMBS_DOWN, Emoji.STOP_SHIELD);
	private static final String CREATOR_KEY = "Creator";

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		String voteMessage = String.join(" ", params);

		Localisation title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOTE_START_TITLE, guild);
		Localisation footer = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOTE_START_FOOTER, guild);

		return MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
				messageSpec.setTitle(String.format(title.getValue(), member.getDisplayName()));
				messageSpec.setDescription(voteMessage);
				messageSpec.setFooter(String.format(footer.getValue(), member.getDisplayName(), VOTE_EMOJIS.get(2)), null);
			}))
			.doOnSuccess(m -> GuildReactionListener.getGuildListener(guild).addListener(m.getId(), new ReactionHandler(Vote::executeFromReaction).addData(CREATOR_KEY, member)))
			.flatMap(m -> Flux.fromIterable(VOTE_EMOJIS)
					.flatMap(emoji -> m.addReaction(ReactionEmoji.unicode(emoji)))
					.next()
			).then();
	}

	// TODO: We have a potential bug here. If someone deletes a vote message, we won't remove the message ID from the ACTIVE_VOTE_MESSAGE_IDS map. - fixed by adding generic way to handle reaction stream
	// So technically someone could fill up this list with IDs and fill up our memory :/
	public static Mono<Void> executeFromReaction(ReactionAddEvent event, MessageChannel channel, Message message, String unicode, Member member, Guild guild, ReactionHandler.MetaData metaData) {
		if (unicode.equals(Emoji.STOP_SHIELD)) {
			Member creator = metaData.getParsed(CREATOR_KEY, Member.class);
			if (!creator.equals(member)) {
				// cant close poll unless you created it
				return Mono.empty();
			}

			// outsourced counting as to not require a hack for it to work in the lambda
			HashMap<String, Double> reactionCount = countReactions(message.getReactions());

			//making final doubles for output
			final double forIt = reactionCount.get(Emoji.THUMBS_UP);
			final double againstIt = reactionCount.get(Emoji.THUMBS_DOWN);

			final double allVotes = forIt + againstIt;
			// calculate percentage and round to 2 decimal places
			final double percentageForIt = (double) Math.round((forIt / allVotes * 100) * 100) / 100;
			final double percentageAgainstIt = (double) Math.round((againstIt / allVotes * 100) * 100) / 100;


			GuildReactionListener.removeListener(guild, message.getId());

			Localisation title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOTE_END_TITLE, guild);
			Localisation desc = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOTE_END_DESC, guild);

			String messageContent = message.getEmbeds().get(0).getDescription().get();

			return MessageHandler.edit(message, MessageSpec.getDefaultSpec(messageSpec -> {
				messageSpec.setTitle(title.getValue());
				messageSpec.setDescription(String.format(desc.getValue(), messageContent, (int) forIt, percentageForIt, (int) againstIt, percentageAgainstIt, (int) allVotes));
			}))
			.flatMap(Message::removeAllReactions)
			.then();
		}

		return Mono.empty();
	}

	private static HashMap<String, Double> countReactions(Set<Reaction> reactions) {
		HashMap<String, Double> out = new HashMap<>();
		for (Reaction reaction : reactions) {
			if (reaction.selfReacted()) { //only consider options the bot set
				String currentEmoji = reaction.getEmoji().asUnicodeEmoji().get().getRaw();
				Double count = (double) reaction.getCount() - 1; // subtracting one to ignore heras reaction
				out.put(currentEmoji, count);
			}
		}
		return out;
	}
}
