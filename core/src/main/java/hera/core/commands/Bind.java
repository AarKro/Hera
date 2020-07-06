package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.*;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Binding;
import hera.database.entities.BindingType;
import hera.database.types.SnowflakeType;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class Bind {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		try {
			List<BindingType> bTypes = STORE.bindingTypes().forName(params.get(0).trim().toUpperCase());
			if (!bTypes.isEmpty()) {
				BindingType bindingType = bTypes.get(0);
				switch (bindingType.getSnowflakeType()) {
					case ROLE:
						Mono<Role> role = HeraUtil.getRoleFromMention(guild, params.get(1));
						return role.flatMap(r -> HeraUtil.hasRightsToSetRole(guild, r).flatMap(b -> {
							List<Binding> bindings = STORE.bindings().forGuildAndType(guild.getId().asLong(), bindingType);
							if (bindings.isEmpty()) {
								STORE.bindings().add(new Binding(guild.getId().asLong(), bindingType, r.getId().asLong()));
							} else {
								Binding binding = bindings.get(0);
								binding.setSnowflake(r.getId().asLong());
								STORE.bindings().update(binding);
							}
							return MessageHandler.send(channel, MessageSpec.getDefaultSpec(s -> s.setDescription(bindingType.getMessage().getValue()))).then();
						}));
					case CHANNEL:
					default:
						Mono<GuildChannel> cnl = HeraUtil.getChannelFromMention(guild, params.get(1));
						return cnl.flatMap(c -> {
							List<Binding> bindings = STORE.bindings().forGuildAndType(guild.getId().asLong(), bindingType);
							if (bindings.isEmpty()) {
								STORE.bindings().add(new Binding(guild.getId().asLong(), bindingType, c.getId().asLong()));
							} else {
								Binding binding = bindings.get(0);
								binding.setSnowflake(c.getId().asLong());
								STORE.bindings().update(binding);
							}
							return MessageHandler.send(channel, MessageSpec.getDefaultSpec(s -> s.setDescription(bindingType.getMessage().getValue()))).then();
						});
				}
			}


		}
		catch (Exception e ) {
			e.printStackTrace();
		}
		return Mono.empty();

	}
}
