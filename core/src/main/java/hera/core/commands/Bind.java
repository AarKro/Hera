package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.*;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Binding;
import hera.database.entities.BindingType;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import hera.database.types.SnowflakeType;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class Bind {

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		List<BindingType> bTypes = STORE.bindingTypes().forName(params.get(0).trim().toUpperCase());
		if (!bTypes.isEmpty()) {
			BindingType bindingType = bTypes.get(0);
			if (!bindingType.isGlobal() || STORE.owners().isOwner(member.getId().asLong())) {
				Mono<GuildChannel> cnl = HeraUtil.getChannelFromMention(guild, params.get(1));

				if (cnl == null) return MessageHandler.send(channel, MessageSpec.getErrorSpec(spec -> {
					Localisation local = HeraUtil.getLocalisation(LocalisationKey.BINDING_ERROR_CHANNEL, guild);
					spec.setDescription(String.format(local.getValue(), params.get(1)));
				})).then();

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
		return Mono.empty();

	}

}
