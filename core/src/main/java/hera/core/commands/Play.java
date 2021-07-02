package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.api.handlers.YouTubeApiHandler;
import hera.core.music.HeraAudioManager;
import hera.database.entities.ConfigFlag;
import hera.database.entities.ConfigFlagType;
import hera.database.types.ConfigFlagName;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class Play {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		String songUrl = "";
		if (params.get(0).matches("(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")) {
			songUrl = params.get(0);
		} else {
			String keywords = String.join(" ", params);
			songUrl = YouTubeApiHandler.getYoutubeVideoFromKeyword(keywords);
		}

		HeraAudioManager.playerManager.loadItem(songUrl, HeraAudioManager.getLoadResultHandler(guild, channel));

		// join voice channel of member if config flag is on
		List<ConfigFlagType> type = STORE.configFlagTypes().forName(ConfigFlagName.JOIN_ON_PLAY);
		List<ConfigFlag> flags = STORE.configFlags().forGuildAndType(guild.getId().asLong(), type.get(0));
		if (!flags.isEmpty() && flags.get(0).getValue()) {
			return member.getVoiceState()
					.flatMap(VoiceState::getChannel)
					.flatMap(vChannel -> vChannel.join(spec -> spec.setProvider(HeraAudioManager.getProvider(guild))))
					.doOnNext(vc -> HeraAudioManager.addVC(guild, vc))
					.then();
		}

		return Mono.empty();
	}
}
