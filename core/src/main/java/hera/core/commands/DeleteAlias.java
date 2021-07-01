package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static hera.store.DataStore.STORE;

public class DeleteAlias {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        List<hera.database.entities.Alias> found = STORE.aliases().forGuildAndAlias(guild.getId().asLong(), params.get(0));
        List<hera.database.entities.Alias> aliasToDelete = found.stream().filter(alias -> alias.getGuild() != null).collect(Collectors.toList());

        if (!aliasToDelete.isEmpty()) {
            STORE.aliases().delete(aliasToDelete.get(0).getId());
            return MessageHandler.send(channel, MessageSpec.getConfirmationSpec(spec -> {
                spec.setDescription(String.format(HeraUtil.getLocalisation(LocalisationKey.COMMAND_DELETEALIAS, guild).getValue(), params.get(0)));
            })).then();
        } else {
            LocalisationKey key = found.size() > aliasToDelete.size() ? LocalisationKey.COMMAND_DELETEALIAS_ERROR_GLOBAL : LocalisationKey.COMMAND_DELETEALIAS_ERROR;
            return MessageHandler.send(channel, MessageSpec.getErrorSpec(spec -> {
                spec.setDescription(String.format(HeraUtil.getLocalisation(key, guild).getValue(), params.get(0)));
            })).then();
        }
    }
}
