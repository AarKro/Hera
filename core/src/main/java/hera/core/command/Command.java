package hera.core.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public interface Command {
    Mono<Void> execute(MessageCreateEvent event);
}
