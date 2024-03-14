package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.music.HeraAudioManager;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

public class Resume {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return resumePlayer(guild)
				.flatMap(m -> MessageHandler.send(channel, MessageSpec.getConfirmationSpec(messageSpec -> messageSpec.setDescription(m))).then()
		);
	}

	private static Mono<String> resumePlayer(Guild guild) {
		Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_RESUMED, guild);
		String message = local.getValue();
		if (HeraAudioManager.getPlayer(guild).isPaused()) {
			HeraAudioManager.getPlayer(guild).setPaused(false);
		} else {
			message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_RESUMED_ERROR, guild).getValue();
		}

		return Mono.just(message);
	}
}
