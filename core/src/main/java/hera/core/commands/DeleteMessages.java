package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import reactor.core.publisher.Mono;

import java.util.List;

public class DeleteMessages {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		try {
			int deleteAmount = Integer.parseInt(params.get(0));
			if (deleteAmount < 1) {
				return channel.createMessage(HeraUtil.LOCALISATION_PARAM_ERROR.getValue()).then();
			}
			return channel.getLastMessage()
					.flatMap(message -> channel.getMessagesBefore(message.getId())
							.take(deleteAmount)
							.flatMap(msg -> msg.delete())
							.next()
							.then(message.delete())
					).then();
		} catch (NumberFormatException e) {
			return channel.createMessage(HeraUtil.LOCALISATION_PARAM_ERROR.getValue()).then();
		}
	}
}
