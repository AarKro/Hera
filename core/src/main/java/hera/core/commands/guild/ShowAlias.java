package hera.core.commands.guild;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.messages.formatter.list.ListMaker;
import hera.database.entities.Alias;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.core.messages.formatter.DefaultStrings.NEW_LINE;
import static hera.core.messages.formatter.markdown.MarkdownHelper.makeBold;
import static hera.core.messages.formatter.markdown.MarkdownHelper.makeItalics1;
import static hera.core.util.LocalisationUtil.LOCALISATION_PARAM_ERROR;
import static hera.core.util.LocalisationUtil.getLocalisation;
import static hera.store.DataStore.STORE;

public class ShowAlias {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        if (params.isEmpty()) {
            Localisation titleLocal = getLocalisation(LocalisationKey.COMMAND_ALIAS_TITLE, guild);
            Localisation globalLocal = getLocalisation(LocalisationKey.COMMAND_ALIAS_GLOBAL, guild);
            Localisation guildLocal = getLocalisation(LocalisationKey.COMMAND_ALIAS_GUILD, guild);
            Localisation noneLocal = getLocalisation(LocalisationKey.COMMAND_ALIAS_NONE, guild);
            ListMaker<Alias> aliasListGen = new ListMaker<>(" %s: %s",(i, a) -> ListMaker.argumentMaker(a.getCommand().getName().toString().toLowerCase(), a.getAlias().toLowerCase()));


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
                spec.setDescription(LOCALISATION_PARAM_ERROR.getValue());
            })).then();
        }
    }
}
