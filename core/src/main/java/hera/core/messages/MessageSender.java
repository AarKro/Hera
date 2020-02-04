package hera.core.messages;

import discord4j.core.object.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.awt.*;

public class MessageSender {

	private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

	public static Mono<Message> send(HeraMsgSpec heraMessageSpec) {
		return heraMessageSpec.getChannel().createMessage(messageCreateSpec -> messageCreateSpec.setEmbed(embedCreateSpec -> {
			if (heraMessageSpec.getTitle() != null) embedCreateSpec.setTitle(heraMessageSpec.getTitle());
			if (heraMessageSpec.getDescription() != null) embedCreateSpec.setDescription(heraMessageSpec.getDescription());
			if (heraMessageSpec.getFooterText() != null) embedCreateSpec.setFooter(heraMessageSpec.getFooterText(), null);
			if (heraMessageSpec.getColor() != null) embedCreateSpec.setColor(heraMessageSpec.getColor());
		}));
	}
}
