package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.database.entities.mapped.GuildSettings;
import hera.database.entities.mapped.Localisation;
import hera.database.types.GuildSettingKey;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;
import static hera.store.DataStore.STORE;

import java.util.List;

public class Prefix {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		GuildSettings guildSettings = new GuildSettings(guild.getId().asLong(), GuildSettingKey.COMMAND_PREFIX, params.get(0));
		STORE.guildSettings().upsert(guildSettings);
		Localisation message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_PREFIX, guild);
		return channel.createMessage(String.format(message.getValue(), guildSettings.getValue())).then();
	}
}
