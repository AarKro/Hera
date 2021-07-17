package hera.core.util;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.channel.GuildChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DiscordUtil {
	public static final Logger LOG = LoggerFactory.getLogger(DiscordUtil.class);

	public static boolean isUserMention(String string) {
		return string.matches("<@!\\d{1,50}>");
	}

	public static Long getIdUserFromString(String mention) {
		return Long.parseLong(mention.substring(3, mention.length()-1));
	}

	public static Mono<Member> getUserFromMention(Guild guild, String mention) {
		if (isUserMention(mention)) {
			return guild.getMemberById(Snowflake.of(getIdUserFromString(mention))).onErrorResume(throwable -> {
				LOG.error("Error when trying to parse supplied user id: {}", mention);
				LOG.error("Stacktrace", throwable);
				return Mono.empty();
			});
		}
		return Mono.empty();
	}

	public static boolean isRoleMention(String string) {
		return string.matches("<@&\\d{1,50}>");
	}

	public static Long getRoleIdFromString(String mention) {
		return Long.parseLong(mention.substring(3, mention.length()-1));
	}

	public static Mono<Role> getRoleFromMention(Guild guild, String mention) {
		if (isRoleMention(mention)) {
			return guild.getRoleById(Snowflake.of(getRoleIdFromString(mention))).onErrorResume(throwable -> {
				LOG.error("Error when trying to parse supplied role id: {}", mention);
				LOG.error("Stacktrace", throwable);
				return Mono.empty();
			});
		}
		return Mono.empty();
	}

	public static boolean isChannelMention(String string) {
		return string.matches("<#\\d{1,50}>");
	}

	public static Long getIdChannelFromString(String mention) {
		return Long.parseLong(mention.substring(2, mention.length()-1));
	}

	public static Mono<GuildChannel> getChannelFromMention(Guild guild, String mention) {
		if (isChannelMention(mention)) {
			return guild.getChannelById(Snowflake.of(getIdChannelFromString(mention))).onErrorResume(throwable -> {
				LOG.error("Error when trying to parse supplied channel id: {}", mention);
				LOG.error("Stacktrace", throwable);
				return Mono.empty();
			});
		}
		return Mono.empty();
	}

	public static Flux<Member> getDiscordUser(Guild guild, String user) {
		return guild.getMembers().filter(member -> member.getDisplayName().toUpperCase().equals(user.toUpperCase()));
	}

	public static Flux<Member> getDiscordUser(Guild guild, Long user) {
		return guild.getMembers().filter(member -> user.equals(member.getId().asLong()));
	}

}
