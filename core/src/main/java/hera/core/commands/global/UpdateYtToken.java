package hera.core.commands.global;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.api.handlers.YouTubeApiHandler;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Token;
import hera.database.types.TokenKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class UpdateYtToken {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        String name = params.get(0);
        Token nameToken = STORE.tokens().forKey(TokenKey.YOUTUBE_API_APP_NAME).get(0);
        nameToken.setToken(name);
        STORE.tokens().update(nameToken);
        String token = params.get(1);
        Token tokenToken = STORE.tokens().forKey(TokenKey.YOUTUBE_API_TOKEN).get(0);
        tokenToken.setToken(token);
        STORE.tokens().update(tokenToken);

        // re-initialise the API handler so it will retrieve the new tokens from the database
        YouTubeApiHandler.initialise();

        return MessageHandler.send(channel, MessageSpec.getConfirmationSpec(messageSpec -> {
            messageSpec.setDescription("Successfully updated YouTube app name and YouTube app token");
        })).then();
    }
}
