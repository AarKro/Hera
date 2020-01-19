package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.database.entities.Command;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

import static hera.store.DataStore.STORE;

public class Alias {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        List<Command> commands = STORE.commands().forName(params.get(0));
        if (commands.isEmpty()) {
            return channel.createMessage(spec -> spec.setEmbed(embed -> {
                embed.setColor(Color.ORANGE);
                embed.setDescription(String.format(HeraUtil.getLocalisation(LocalisationKey.ERROR_NOT_REAL_COMMAND, guild).getValue(), params.get(0)));
            })).then();
        }
        Command command = commands.get(0);
        String alias = params.get(1).toUpperCase();

        if (!STORE.aliases().exists(alias, guild.getId().asLong())) {
            STORE.aliases().add(new hera.database.entities.Alias(command, alias, guild.getId().asLong()));
        }
        return Mono.empty();
    }
}
