package hera.core.messages;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

public class MessageHandler {

	public static Mono<Message> send(MessageChannel channel, MessageSpec messageSpec) {
		return channel.createMessage(mcs -> mcs.setEmbed(embedCreateSpec -> fillEmbedSpec(embedCreateSpec, messageSpec)));
	}

	public static Mono<Message> edit(Message message, MessageSpec messageSpec) {
		return message.edit(mes -> mes.setEmbed(embedCreateSpec -> fillEmbedSpec(embedCreateSpec, messageSpec)));
	}

	private static void fillEmbedSpec(EmbedCreateSpec embedSpec, MessageSpec messageSpec) {
		if (messageSpec.getTitle() != null) embedSpec.setTitle(messageSpec.getTitle());
		if (messageSpec.getDescription() != null) embedSpec.setDescription(messageSpec.getDescription());
		if (messageSpec.getFooterText() != null) embedSpec.setFooter(messageSpec.getFooterText(), null);
		if (messageSpec.getColor() != null) embedSpec.setColor(messageSpec.getColor());
	}
}
