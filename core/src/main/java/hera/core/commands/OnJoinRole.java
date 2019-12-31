package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Snowflake;
import hera.core.HeraUtil;
import hera.database.entities.mapped.GuildSetting;
import hera.database.entities.mapped.Localisation;
import hera.database.types.GuildSettingKey;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

import static hera.store.DataStore.STORE;

public class OnJoinRole {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Mono<Role> role = HeraUtil.getRoleFromMention(guild, params.get(0));
		return role.flatMap(r ->
				HeraUtil.hasRightsToSetRole(guild, r).flatMap(b -> {
					if(b) {
						GuildSetting guildSetting = new GuildSetting(guild.getId().asLong(), GuildSettingKey.ON_JOIN_ROLE, r.getId().asString());
						STORE.guildSettings().upsert(guildSetting);
						Localisation message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ON_JOIN_ROLE, guild);
						return channel.createMessage(spec -> spec.setEmbed(embed -> {
							embed.setColor(Color.ORANGE);
							embed.setDescription(String.format(message.getValue(), r.getMention()));
						})).then();
					}
					Localisation message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ON_JOIN_ROLE_ERROR, guild);
					return channel.createMessage(spec -> spec.setEmbed(embed -> {
						embed.setColor(Color.ORANGE);
						embed.setDescription(String.format(message.getValue(), r.getMention()));
					})).then();
					}).then()
		).then();
	}

	public static Mono<Void> executeOld(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Mono<Role> role = getRoleFromMention(guild, params.get(0));
		return role.flatMap(r -> {
			GuildSetting guildSetting = new GuildSetting(guild.getId().asLong(), GuildSettingKey.ON_JOIN_ROLE, r.getId().asString());
			STORE.guildSettings().upsert(guildSetting);
			Localisation message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ON_JOIN_ROLE, guild);
			return channel.createMessage(spec -> spec.setEmbed(embed -> {
				embed.setColor(Color.ORANGE);
				embed.setDescription(String.format(message.getValue(), r.getMention()));
			})).then();
		}).then();
	}



	public static boolean isRoleMention(String string) {
		return string.matches("<@&\\d{1,50}>");
	}

	public static Long getIdFromString(String mention) {
		return Long.parseLong(mention.substring(3, mention.length()-1));
	}

	public static Mono<Role> getRoleFromMention(Guild guild, String mention) {
		if (isRoleMention(mention)) {
			return guild.getRoleById(Snowflake.of(getIdFromString(mention)));
		}
		return Mono.empty();
	}


}
