package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.GuildChannel;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Binding;
import hera.database.entities.BindingType;
import hera.database.types.BindingName;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class Bind {

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		BindingName name;
		try {
			name = BindingName.valueOf(params.get(0).toUpperCase());
		} catch (IllegalArgumentException exception) {
			return MessageHandler.send(channel, MessageSpec.getDefaultSpec(s -> s.setDescription(HeraUtil.getLocalisation(LocalisationKey.BINDING_ERROR_EXIST, guild).getValue()))).then();
		}
		List<BindingType> bTypes = STORE.bindingTypes().forName(name);
		if (!bTypes.isEmpty()) {
			BindingType bindingType = bTypes.get(0);
			if (!bindingType.isGlobal() || STORE.owners().isOwner(member.getId().asLong())) {
			    Mono<GuildChannel> cnl;

			    if (params.size() < 2) {
			        cnl = Mono.just((GuildChannel) channel);
                } else {
                    cnl = HeraUtil.getChannelFromMention(guild, params.get(1));
                }

				return cnl.flatMap(c -> {

					List<Binding> bindings;
					if (bindingType.isGlobal()) {
						bindings = STORE.bindings().forType(bindingType);
					} else {
						bindings = STORE.bindings().forGuildAndType(guild.getId().asLong(), bindingType);
					}

					if (bindings.isEmpty()) {
						STORE.bindings().add(new Binding(guild.getId().asLong(), bindingType, c.getId().asLong()));
					} else {
						Binding binding = bindings.get(0);
						binding.setSnowflake(c.getId().asLong());
						if (bindingType.isGlobal()) binding.setGuild(guild.getId().asLong());
						STORE.bindings().update(binding);
					}

					return MessageHandler.send(channel, MessageSpec.getDefaultSpec(s -> s.setDescription(bindingType.getMessage().getValue()))).then();
				});
			} else {
				return MessageHandler.send(channel, MessageSpec.getDefaultSpec(s -> s.setDescription(HeraUtil.getLocalisation(LocalisationKey.BINDING_ERROR_EXIST, guild).getValue()))).then();
			}
		} else {
			return MessageHandler.send(channel, MessageSpec.getDefaultSpec(s -> s.setDescription(HeraUtil.getLocalisation(LocalisationKey.BINDING_ERROR_EXIST, guild).getValue()))).then();
		}
	}

}
