package hera.core.commands.guild;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.ConfigFlag;
import hera.database.entities.ConfigFlagType;
import hera.database.entities.Localisation;
import hera.database.types.ConfigFlagName;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static hera.core.HeraUtil.LOG;
import static hera.store.DataStore.STORE;

public class Config {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_CONFIG, guild);
		Localisation localEnabled = HeraUtil.getLocalisation(LocalisationKey.COMMON_ENABLED, guild);
		Localisation localDisabled = HeraUtil.getLocalisation(LocalisationKey.COMMON_DISABLED, guild);

		if (params.size() == 0) {
			// list all config flags and their current value
			StringBuilder message = new StringBuilder();
			List<ConfigFlag> flags = STORE.configFlags().forGuild(guild.getId().asLong());

			flags.forEach(flag -> {
				message.append(flag.getConfigFlagType().getName().toString().toLowerCase());
				message.append(": ");
				message.append(flag.getValue() ? localEnabled.getValue() : localDisabled.getValue());
				message.append("\n");
			});

			return MessageHandler.send(channel, MessageSpec.getDefaultSpec(spec -> {
				spec.setTitle(local.getValue());
				spec.setDescription(message.toString());
			})).then();
		} else if (params.size() == 1) {
			try {
				ConfigFlagName flag = ConfigFlagName.valueOf(params.get(0).toUpperCase());
				List<ConfigFlagType> type = STORE.configFlagTypes().forName(flag);
				List<ConfigFlag> flags = STORE.configFlags().forGuildAndType(guild.getId().asLong(), type.get(0));
				final AtomicBoolean flagValue = new AtomicBoolean(true);

				ConfigFlag flagToUpdate = flags.get(0);
				flagToUpdate.setValue(!flagToUpdate.getValue());
				flagValue.set(flagToUpdate.getValue());
				STORE.configFlags().update(flagToUpdate);

				return MessageHandler.send(channel, MessageSpec.getDefaultSpec(spec -> {
					spec.setDescription(flag.toString().toLowerCase() + " " + (flagValue.get() ? localEnabled.getValue() : localDisabled.getValue()));
				})).then();
			} catch (Exception exception) {
				LOG.debug("Stacktrace", exception);
				return MessageHandler.send(channel, MessageSpec.getErrorSpec(spec -> {
					Localisation errorLocal = HeraUtil.getLocalisation(LocalisationKey.COMMAND_CONFIG_ERROR, guild);
					spec.setDescription(String.format(errorLocal.getValue(), params.get(0)));
				})).then();
			}
		}

		return Mono.empty();
	}
}
