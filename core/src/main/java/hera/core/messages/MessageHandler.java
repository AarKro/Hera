package hera.core.messages;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateFields;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import reactor.core.publisher.Mono;

public class MessageHandler {

	public static Mono<Message> send(MessageChannel channel, MessageSpec messageSpec) {
		return channel.createMessage(MessageCreateSpec.builder()
				.addEmbed(getFilledEmbedSpec(messageSpec))
				.build());
	}

	public static Mono<Message> edit(Message message, MessageSpec messageSpec) {
		return message.edit(MessageEditSpec.builder()
				.addEmbed(getFilledEmbedSpec(messageSpec))
				.build());
	}

	private static EmbedCreateSpec getFilledEmbedSpec(MessageSpec messageSpec) {
		EmbedCreateSpec.Builder spec = EmbedCreateSpec.builder();

		if (messageSpec.getTitle() != null) spec.title(messageSpec.getTitle());
		if (messageSpec.getDescription() != null) spec.description(messageSpec.getDescription());
		if (messageSpec.getFooterText() != null) spec.footer(EmbedCreateFields.Footer.of(messageSpec.getFooterText(), messageSpec.getFooterIconUrl()));
		if (messageSpec.getColor() != null) spec.color(messageSpec.getColor());

		return spec.build();
	}
}
