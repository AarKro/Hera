package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.database.entities.mapped.GlobalSettings;
import hera.database.entities.mapped.Localisation;
import hera.database.types.GlobalSettingKey;
import hera.database.types.LocalisationKey;
import hera.store.unit.AliasAccessUnit;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class Alias {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        makeAlias(guild, params);
        return Mono.empty();
    }

    private static void makeAlias(Guild guild, List<String> params) {
        int commandId = STORE.commands().forName(params.get(0)).get(0).getId();
        String alias = params.get(1);

        if (!STORE.alias().exists(alias, guild.getId().asLong())) {
            STORE.alias().add(new hera.database.entities.mapped.Alias(commandId, alias, guild.getId().asLong()));
        }

    }
}
