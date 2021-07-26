package hera.core.events.reactions;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

import java.util.HashMap;

//TODO maybe move listener to different class? Maybe have a more multithreadcompatible way of handling this since it may become a bottleneck, for now is fine though
public class GuildReactionListener extends HashMap<Snowflake, ReactionHandler> {
	private static HashMap<Snowflake, GuildReactionListener> guildListeners =  new HashMap<>();

	public static Boolean hasGuildListener(Guild guild) {
		return guildListeners.containsKey(guild.getId());
	}

	public static GuildReactionListener getGuildListener(Guild guild) {
		if (!guildListeners.containsKey(guild.getId())) {
			guildListeners.put(guild.getId(), new GuildReactionListener());
		}
		return guildListeners.get(guild.getId());
	}

	public static void addListener(Guild guild, Snowflake messageId, IReactionHandler handler) {
		getGuildListener(guild).addListener(messageId, handler);
	}

	public static void addListener(Guild guild, Snowflake messageId, ReactionHandler handler) {
		getGuildListener(guild).addListener(messageId, handler);
	}

	public static void removeListener(Guild guild, Snowflake messageId) {
		if (hasGuildListener(guild)) getGuildListener(guild).removeListener(messageId);
	}

	public void addListener(Snowflake messageId, IReactionHandler handler) {
		put(messageId, new ReactionHandler(handler));
	}

	public void addListener(Snowflake messageId, ReactionHandler handler) {
		put(messageId, handler);
	}

	public void removeListener(Snowflake messageId) {
		remove(messageId);
	}

	public Mono<Void> handleReaction(ReactionAddEvent event, MessageChannel channel, Message message, Snowflake messageId, String emojiUnicode, Member member, Guild guild) {
		if (containsKey(messageId)) {
			ReactionHandler reactionHandler = get(messageId);
			return reactionHandler.getHandler().executeFromReaction(event, channel, message, emojiUnicode, member, guild, reactionHandler.getMetaData());
		}
		return Mono.empty();
	}
}
