package hera.core.commands.music;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.music.HeraAudioManager;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.core.util.LocalisationUtil.getLocalisation;

public class Pause {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return pausePlayer(guild).flatMap(m -> MessageHandler.send(channel, MessageSpec.getConfirmationSpec(messageSpec -> {
			messageSpec.setDescription(m);
		}))).then();
	}

	private static Mono<String> pausePlayer(Guild guild) {
		Localisation local = getLocalisation(LocalisationKey.COMMAND_PAUSED, guild);
		String message = local.getValue();
		if (!HeraAudioManager.getPlayer(guild).isPaused()) {
			HeraAudioManager.getPlayer(guild).setPaused(true);
		} else {
			message = getLocalisation(LocalisationKey.COMMAND_PAUSED_ERROR, guild).getValue();
		}

		return Mono.just(message);
	}
}
