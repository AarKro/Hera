package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.music.HeraAudioManager;
import reactor.core.publisher.Mono;

import java.util.List;

public class Play {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return Mono.justOrEmpty(HeraAudioManager.playerManager.loadItem(params.get(0), HeraAudioManager.getLoadResultHandler(guild, channel))).then();
	}
}
