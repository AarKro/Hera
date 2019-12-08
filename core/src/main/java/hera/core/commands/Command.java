package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.database.types.CommandName;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Command {

    Map<CommandName, Command> COMMANDS = new HashMap<>();

    static void initialise() {
        COMMANDS.put(CommandName.UPTIME, Uptime::execute);
    }

    Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params);
}
