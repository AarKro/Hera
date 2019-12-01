package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Mono;

import java.util.List;

public interface Command {
    Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, List<String> params);
}
