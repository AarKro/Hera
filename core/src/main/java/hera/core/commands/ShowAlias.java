package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.messages.formatter.DefaultStrings;
import hera.core.messages.formatter.list.ListGen;
import hera.core.messages.formatter.TextFormatter;
import hera.database.entities.Alias;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class ShowAlias {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        if (params.isEmpty()) {
            Localisation titleLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_TITLE, guild);
            Localisation globalLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_GLOBAL, guild);
            Localisation guildLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_GUILD, guild);
            Localisation noneLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_NONE, guild);
            ListGen<Alias> aliasListGen = new ListGen<Alias>()
                    .setNodes(" %s: %s") //
                    .addItemConverter(a -> a.getCommand().getName().toString().toLowerCase())
                    .addItemConverter(a -> a.getAlias().toLowerCase());


            StringBuilder message = new StringBuilder();

            message.append(TextFormatter.encaseWith(guildLocal.getValue(), DefaultStrings.BOLD));
            message.append(DefaultStrings.NEW_LINE);

            List<Alias> guildAliases = STORE.aliases().forGuild(guild.getId().asLong());
            if (!guildAliases.isEmpty()) {  // if there is aliases list them
                message.append(aliasListGen.setItems(guildAliases).makeList());
            } else { // otherwise output that there is none
                message.append(TextFormatter.encaseWith(noneLocal.getValue(), DefaultStrings.ITALICS1));
                message.append(DefaultStrings.NEW_LINE);
            }

            message.append(DefaultStrings.NEW_LINE);

            message.append(TextFormatter.encaseWith(globalLocal.getValue(), DefaultStrings.BOLD));
            message.append(DefaultStrings.NEW_LINE);

            List<Alias> globalAliases = STORE.aliases().forGuild(null);
            if (!globalAliases.isEmpty()) {
                message.append(aliasListGen.setItems(globalAliases).makeList());
            } else {
                message.append(TextFormatter.encaseWith(noneLocal.getValue(), DefaultStrings.ITALICS1));
            }

            return MessageHandler.send(channel, MessageSpec.getDefaultSpec(spec -> {
                spec.setTitle(titleLocal.getValue());
                spec.setDescription(message.toString());
            })).then();
        } else {
            return MessageHandler.send(channel, MessageSpec.getErrorSpec(spec -> {
                spec.setDescription(HeraUtil.LOCALISATION_PARAM_ERROR.getValue());
            })).then();
        }
    }
}
