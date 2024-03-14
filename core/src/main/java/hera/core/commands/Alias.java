package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Command;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class Alias {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        if (params.size() == 2) {
            List<Command> commands = STORE.commands().forName(params.get(0));
            if (commands.isEmpty()) {
                return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> {
                    messageSpec.setDescription(String.format(HeraUtil.getLocalisation(LocalisationKey.ERROR_NOT_REAL_COMMAND, guild).getValue(), params.get(0)));
                })).then();
            }
            Command command = commands.get(0);
            String alias = params.get(1).toUpperCase();

            if (!STORE.aliases().exists(alias, guild.getId().asLong())) {
                STORE.aliases().add(new hera.database.entities.Alias(command, alias, guild.getId().asLong()));

                return MessageHandler.send(channel, MessageSpec.getConfirmationSpec(spec -> {
                    spec.setDescription(String.format(HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_NEW, guild).getValue(), alias.toLowerCase(), command.getName().toString().toLowerCase()));
                })).then();
            }
            return Mono.empty();
        } else if (params.isEmpty()) {
            Localisation titleLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_TITLE, guild);
            Localisation globalLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_GLOBAL, guild);
            Localisation guildLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_GUILD, guild);
            Localisation noneLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_ALIAS_NONE, guild);

            List<hera.database.entities.Alias> aliases = STORE.aliases().forGuild(guild.getId().asLong());
            StringBuilder message = new StringBuilder();
            message.append("**");
            message.append(guildLocal.getValue());
            message.append("**\n");
            if (aliases.isEmpty()) {
                message.append("_");
                message.append(noneLocal.getValue());
                message.append("_");
            } else {
                aliases.forEach(alias -> {
                    message.append(alias.getCommand().getName().toString().toLowerCase());
                    message.append(": ");
                    message.append(alias.getAlias().toLowerCase());
                    message.append("\n");
                });
            }

            message.append("\n");
            if (aliases.isEmpty()) message.append("\n");

            List<hera.database.entities.Alias> globals = STORE.aliases().forGuild(null);
            message.append("**");
            message.append(globalLocal.getValue());
            message.append("**\n");
            if (globals.isEmpty()) {
                message.append("_");
                message.append(noneLocal.getValue());
                message.append("_");
            } else {
                globals.forEach(alias -> {
                    message.append(alias.getCommand().getName().toString().toLowerCase());
                    message.append(": ");
                    message.append(alias.getAlias().toLowerCase());
                    message.append("\n");
                });
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
