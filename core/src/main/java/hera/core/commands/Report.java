package hera.core.commands;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Binding;
import hera.database.entities.Localisation;
import hera.database.types.BindingName;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static hera.store.DataStore.STORE;

public class Report {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        List<Binding> bindings = STORE.bindings().forGuildAndType(guild.getId().asLong(), STORE.bindingTypes().forName(BindingName.REPORT).get(0));
        if (!bindings.isEmpty()) {
            Binding binding = bindings.get(0);
            String userMention = params.get(0);
            if (HeraUtil.isUserMention(userMention)) {
                return HeraUtil.getUserFromMention(guild, userMention).flatMap(offender ->
                    guild.getChannelById(Snowflake.of(binding.getSnowflake()))
                            .flatMap(chl -> {
                                return MessageHandler.send((MessageChannel) chl, MessageSpec.getDefaultSpec(s -> {
                                    Localisation response = HeraUtil.getLocalisation(LocalisationKey.COMMAND_REPORT_RESPONSE, guild);
                                    String repMention = member.getMention();
                                    String offMention = offender.getMention();
                                    String cause = joinMessage(params, 1);
                                    s.setDescription(String.format(response.getValue(), repMention, offMention, cause));
                                }));
                            }).flatMap(x -> MessageHandler.send(channel, MessageSpec.getConfirmationSpec(s -> s.setDescription(HeraUtil.getLocalisation(LocalisationKey.COMMAND_REPORT_SUBMIT, guild).getValue()))))
                    ).then();
            } else {
                return MessageHandler.send(channel, MessageSpec.getDefaultSpec(s -> s.setDescription(String.format(HeraUtil.getLocalisation(LocalisationKey.ERROR_MENTION_USER, guild).getValue(), userMention)))).then();
            }

        } else {
            return MessageHandler.send(channel, MessageSpec.getDefaultSpec(s -> s.setDescription(HeraUtil.getLocalisation(LocalisationKey.COMMAND_REPORT_ERROR_BINDING, guild).getValue()))).then();
        }
    }

    private static String joinMessage(List<String> params, int startAt) {
        List<String> out = new ArrayList<>();
        for (int i=startAt;i<params.size();i++) {
            out.add(params.get(i));
        }
        return String.join(" ", out);
    }
}
