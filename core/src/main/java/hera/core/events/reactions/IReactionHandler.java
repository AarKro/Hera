package hera.core.events.reactions;

import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public interface IReactionHandler {
	Mono<Void> executeFromReaction(ReactionAddEvent event, MessageChannel channel, Message message, String emojiUnicode, Member member, Guild guild, ReactionHandler.MetaData metaData);
}
