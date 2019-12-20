package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.core.music.HeraAudioManager;
import hera.database.entities.mapped.GuildSettings;
import hera.database.entities.mapped.Localisation;
import hera.database.types.GuildSettingKey;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

import static hera.store.DataStore.STORE;

public class Volume {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return setVolume(guild, params).flatMap(m -> channel.createMessage(spec -> spec.setEmbed(embed -> {
				embed.setColor(Color.ORANGE);
				embed.setDescription(m);
			}))
		)
		.then();
	}

	private static Mono<String> setVolume(Guild guild, List<String> params) {
		Integer volume = Integer.parseInt(params.get(0));

		GuildSettings guildSettings = new GuildSettings(guild.getId().asLong(), GuildSettingKey.VOLUME, volume.toString());
		STORE.guildSettings().upsert(guildSettings);

		HeraAudioManager.getPlayer(guild).setVolume(volume);

		Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VOLUME, guild);
		String message = String.format(local.getValue(), volume);

		return Mono.just(message);
	}
}
