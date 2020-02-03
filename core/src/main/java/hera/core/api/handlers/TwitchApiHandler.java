package hera.core.api.handlers;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitchApiHandler {
    private static final Logger LOG = LoggerFactory.getLogger(TwitchApiHandler.class);

    public static void initialise() {
        String token = "";

//        if (apiTokens.isEmpty()) {
//            LOG.error("Credentials for Twitch API not found");
//            return;
//        }

//        String apiToken = apiTokens.get(0).getToken();

        HttpResponse<String> response = Unirest.post("https://api.twitch.tv/helix/webhooks/hub")
                .header("Client-ID", token)
                .field("hub.callback", "http://localhost:8080")
                .field("hub.mode", "subscribe")
                .field("hub.topic", "https://api.twitch.tv/helix/streams?user_id=141764308")
                .field("hub.lease_seconds", "864000")
                .asString();


    }
}
