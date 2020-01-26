package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.HeraMsgSpec;
import hera.core.messages.MessageSender;
import hera.core.messages.MessageType;
import reactor.core.publisher.Mono;

import java.util.List;

public class DeleteMessages {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		try {
			int deleteAmount = Integer.parseInt(params.get(0));
			if (deleteAmount < 1) {
				return MessageSender.send(new HeraMsgSpec(channel) {{
					setDescription(HeraUtil.LOCALISATION_PARAM_ERROR.getValue());
					setMessageType(MessageType.ERROR);
				}}).then();
			}
			return channel.getLastMessage()
					.flatMap(message -> channel.getMessagesBefore(message.getId())
							.take(deleteAmount)
							.flatMap(Message::delete)
							.next()
							.then(message.delete())
					).then();
		} catch (NumberFormatException e) {
			return MessageSender.send(new HeraMsgSpec(channel) {{
				setDescription(HeraUtil.LOCALISATION_PARAM_ERROR.getValue());
				setMessageType(MessageType.ERROR);
			}}).then();
		}
	}
}
