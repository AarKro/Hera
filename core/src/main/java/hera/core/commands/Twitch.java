package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.database.types.TokenKey;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import reactor.core.publisher.Mono;
import static hera.store.DataStore.STORE;

import java.util.List;

public class Twitch {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        if(params.get(0).equals("user")) {
            HttpResponse<JsonNode> response = Unirest.get("https://api.twitch.tv/helix/users?login=" + params.get(1))
                    .header("Client-ID", "[insert_key_here]")
                    .asJson();
            return channel.createMessage(response.getBody().toPrettyString()).then();
        }
        return channel.createMessage("Nothing to return, sorry :/ To get help, use: `$twitch help`").then();
    }
}
