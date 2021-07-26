package hera.core.commands.guild;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.messages.formatter.list.ListGen;
import hera.core.messages.formatter.TextFormatter;
import hera.database.entities.Alias;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;
import static hera.core.messages.formatter.markdown.MarkdownHelper.*;
import static hera.core.messages.formatter.DefaultStrings.*;

public class ShowAlias {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        if (params.isEmpty()) {
            Localisation titleLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_TITLE, guild);
            Localisation globalLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_GLOBAL, guild);
            Localisation guildLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_GUILD, guild);
            Localisation noneLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_NONE, guild);
            ListGen<Alias> aliasListGen = new ListGen<Alias>()
                    .setNodes(" %s: %s") // %commandName: alias
                    .addItemConverter(a -> a.getCommand().getName().toString().toLowerCase())
                    .addItemConverter(a -> a.getAlias().toLowerCase());


            StringBuilder message = new StringBuilder();

            message.append(makeBold(guildLocal.getValue()));
            message.append(NEW_LINE);

            List<Alias> guildAliases = STORE.aliases().forGuild(guild.getId().asLong());
            if (!guildAliases.isEmpty()) {  // if there is aliases list them
                message.append(aliasListGen.setItems(guildAliases).makeList());
            } else { // otherwise output that there is none
                message.append(makeItalics1(noneLocal.getValue()));
                message.append(NEW_LINE);
            }

            message.append(NEW_LINE);

            message.append(makeBold(globalLocal.getValue()));
            message.append(NEW_LINE);

            List<Alias> globalAliases = STORE.aliases().forGuild(null);
            if (!globalAliases.isEmpty()) {
                message.append(aliasListGen.setItems(globalAliases).makeList());
            } else {
                message.append(makeItalics1(noneLocal.getValue()));
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
