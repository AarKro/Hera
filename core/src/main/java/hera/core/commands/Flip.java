package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

public class Flip {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> messageSpec.setDescription(flipCoin(guild)))).then();
	}

	private static String flipCoin(Guild guild) {
		Random rnd = new Random();
		int i = rnd.nextInt(2);
		Localisation message;
		switch (i) {
			default:
			case 0:
				message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_FLIP_HEADS, guild);
				break;
			case 1:
				message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_FLIP_TAILS, guild);
				break;
		}

		return message.getValue();
	}
}
