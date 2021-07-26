package hera.core.commands.music;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.music.HeraAudioManager;
import hera.database.entities.GuildSetting;
import hera.database.entities.Localisation;
import hera.database.types.GuildSettingKey;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class Volume {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Integer volume = Integer.parseInt(params.get(0));

		if (volume < 0 || volume > 100) {
			Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOLUME_ERROR, guild);
			String message = String.format(local.getValue(), volume);

			return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> messageSpec.setDescription(message))).then();
		}

		List<GuildSetting> gsList = STORE.guildSettings().forGuildAndKey(guild.getId().asLong(), GuildSettingKey.VOLUME);

		if (gsList.isEmpty()) {
			STORE.guildSettings().add(new GuildSetting(guild.getId().asLong(), GuildSettingKey.VOLUME, volume.toString()));
		} else {
			GuildSetting gs = gsList.get(0);
			gs.setValue(volume.toString());
			STORE.guildSettings().update(gs);
		}

		HeraAudioManager.getPlayer(guild).setVolume(volume);

		Localisation local;
		if (volume == 0) {
			local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOLUME_MUTE, guild);
		} else {
			local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOLUME, guild);
		}

		String message = String.format(local.getValue(), volume);

		return MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> messageSpec.setDescription(message))).then();
	}
}
