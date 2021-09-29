package hera.core.messages;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import reactor.core.publisher.Mono;

public class MessageHandler {

	public static Mono<Message> send(MessageChannel channel, MessageSpec messageSpec) {
		return channel.createMessage(MessageCreateSpec.builder().addEmbed(fillEmbedSpec(messageSpec)).build());
	}

	public static Mono<Message> edit(Message message, MessageSpec messageSpec) {
		return message.edit(MessageEditSpec.builder().addEmbed(fillEmbedSpec(messageSpec)).build());
	}

	private static EmbedCreateSpec fillEmbedSpec(MessageSpec messageSpec) {
		EmbedCreateSpec.Builder embedSpecBuilder = EmbedCreateSpec.builder()
				.title(messageSpec.getTitle())
				.description(messageSpec.getDescription())
				.footer(messageSpec.getFooterText(), messageSpec.getFooterIconUrl());
		if (messageSpec.getColor() != null) embedSpecBuilder.color(messageSpec.getColor());
		return embedSpecBuilder.build();
	}
}
