package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.api.handlers.YouTubeApiHandler;
import hera.core.music.HeraAudioManager;
import reactor.core.publisher.Mono;

import java.util.List;

public class Play {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		String songUrl = "";
		if (params.get(0).matches("(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")) {
			songUrl = params.get(0);
		} else {
			String keywords = String.join(" ", params);
			songUrl = YouTubeApiHandler.getYoutubeVideoFromKeyword(keywords);
		}

		HeraAudioManager.playerManager.loadItem(songUrl, HeraAudioManager.getLoadResultHandler(guild, channel));
		return Mono.empty();
	}
}
