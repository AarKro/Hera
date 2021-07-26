package hera.core.commands.music;

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

public class LoopQueue {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		HeraAudioManager.getScheduler(guild).toggleLoopQueue();

		LocalisationKey enabledDisabled;
		if (HeraAudioManager.getScheduler(guild).isLoopQueue()) {
			enabledDisabled = LocalisationKey.COMMON_ENABLED;
		} else {
			enabledDisabled = LocalisationKey.COMMON_DISABLED;
		}
		Localisation loopQueue = HeraUtil.getLocalisation(LocalisationKey.COMMAND_LOOPQUEUE, guild);
		Localisation state = HeraUtil.getLocalisation(enabledDisabled, guild);

		return MessageHandler.send(channel, MessageSpec.getConfirmationSpec(messageSpec -> {
			messageSpec.setDescription(String.format(loopQueue.getValue(), state.getValue()));
		})).then();
	}
}
