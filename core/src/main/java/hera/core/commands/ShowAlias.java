package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.messages.formatter.DefaultStrings;
import hera.core.messages.formatter.ListGenerator;
import hera.core.messages.formatter.TextFormatter;
import hera.core.messages.formatter.list.ParameterList;
import hera.database.entities.Command;
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

            List<hera.database.entities.Alias> aliases = STORE.aliases().forGuild(guild.getId().asLong());
            StringBuilder message = new StringBuilder();
            message.append(TextFormatter.withMarkdown(guildLocal.getValue(), DefaultStrings.BOLD));
            message.append(DefaultStrings.NEW_LINE);
            if (!aliases.isEmpty()) {  // if there is aliases list them
                String guildAliasList = ListGenerator.makeList(" %s: %s\n", aliases,
                        new ParameterList<hera.database.entities.Alias>()
                                .add(a -> a.getCommand().getName().toString().toLowerCase())
                                .add(a -> a.getAlias().toLowerCase()));

            } else { // otherwise output that there is none
                message.append(TextFormatter.withMarkdown(noneLocal.getValue(), DefaultStrings.ITALICS1));
                message.append(DefaultStrings.NEW_LINE);
            }

            message.append(DefaultStrings.NEW_LINE);

            List<hera.database.entities.Alias> globals = STORE.aliases().forGuild(null);
            message.append(TextFormatter.withMarkdown(globalLocal.getValue(), DefaultStrings.BOLD));
            message.append(DefaultStrings.NEW_LINE);
            if (!globals.isEmpty()) {
                message.append(ListGenerator.makeList(" %s: %s\n", globals,
                        new ParameterList<hera.database.entities.Alias>()
                                .add(a -> a.getCommand().getName().toString().toLowerCase())
                                .add(a -> a.getAlias().toLowerCase())));
            } else {
                message.append(TextFormatter.withMarkdown(noneLocal.getValue(), DefaultStrings.ITALICS1));
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
