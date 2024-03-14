package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.GuildSetting;
import hera.database.entities.Localisation;
import hera.database.types.GuildSettingKey;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class OnJoinRole {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Mono<Role> role = HeraUtil.getRoleFromMention(guild, params.get(0));
		return role.flatMap(r ->
				HeraUtil.hasRightsToSetRole(guild, r).flatMap(b -> {
					if(b) {
						List<GuildSetting> gsList = STORE.guildSettings().forGuildAndKey(guild.getId().asLong(), GuildSettingKey.ON_JOIN_ROLE);

						if (gsList.isEmpty()) {
							STORE.guildSettings().add(new GuildSetting(guild.getId().asLong(), GuildSettingKey.ON_JOIN_ROLE, r.getId().asString()));
						} else {
							GuildSetting gs = gsList.get(0);
							gs.setValue(r.getId().asString());
							STORE.guildSettings().update(gs);
						}

						Localisation message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ON_JOIN_ROLE, guild);
						return MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
							messageSpec.setDescription(String.format(message.getValue(), r.getMention()));
						})).then();
					}
					Localisation message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ON_JOIN_ROLE_ERROR, guild);
					return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> {
						messageSpec.setDescription(String.format(message.getValue(), r.getMention()));
					})).then();
				})
		).then();
	}
}
