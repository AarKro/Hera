package hera.core.messages.reaction.emoji;

import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 *
 */
public class EmojiHandler {
	/**
	 *
	 * @param msg
	 * @param emojis
	 * @return
	 */
	public static Mono<Void> addReactions(Message msg, List<String> emojis) {
		return Flux.fromIterable(emojis)
				.flatMap(emoji -> msg.addReaction(ReactionEmoji.unicode(emoji)))
				.next();
	}
}
