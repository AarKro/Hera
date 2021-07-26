package hera.core.commands.music;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.music.HeraAudioManager;
import hera.database.entities.ConfigFlag;
import hera.database.entities.ConfigFlagType;
import hera.database.types.ConfigFlagName;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class Leave {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		HeraAudioManager.dcFromVc(guild);

		// clear queue if config flag is on
		List<ConfigFlagType> type = STORE.configFlagTypes().forName(ConfigFlagName.CLEAR_QUEUE_ON_LEAVE);
		List<ConfigFlag> flags = STORE.configFlags().forGuildAndType(guild.getId().asLong(), type.get(0));
		if (!flags.isEmpty() && flags.get(0).getValue()) {
			HeraAudioManager.getScheduler(guild).clearQueue();
		}

		return Mono.empty();
	}
}
