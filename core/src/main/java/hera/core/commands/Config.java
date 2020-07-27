package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hera.core.HeraUtil.LOG;
import static hera.store.DataStore.STORE;

public class Config {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_CONFIG, guild);
		if (params.size() == 0 || params.get(0).equals("")) {
			// list all config flags and their current value
			StringBuilder message = new StringBuilder();
			List<ConfigFlag> flags = STORE.configFlags().forGuild(guild.getId().asLong());

			Stream.of(ConfigFlagName.values()).forEach(flag -> {
				message.append(flag.toString());
				message.append(": ");
				List<ConfigFlag> guildSetFlag = flags.stream().filter(f -> f.getConfigFlagType().getName() == flag).collect(Collectors.toList());
				if (!guildSetFlag.isEmpty()) {
					// flag has been set for guild
					message.append(guildSetFlag.get(0).getValue() ? "on" : "off");
				} else {
					message.append("off");
				}
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
				if (flags.isEmpty()) {
					STORE.configFlags().add(new ConfigFlag(guild.getId().asLong(), type.get(0), true));
				} else {
					ConfigFlag flagToUpdate = flags.get(0);
					flagToUpdate.setValue(!flagToUpdate.getValue());
					flagValue.set(flagToUpdate.getValue());
					STORE.configFlags().update(flagToUpdate);
				}

				return MessageHandler.send(channel, MessageSpec.getDefaultSpec(spec -> {
					LocalisationKey valueKey;
					if (flagValue.get()) valueKey = LocalisationKey.COMMON_ENABLED;
					else valueKey = LocalisationKey.COMMON_DISABLED;
					Localisation valueLocal = HeraUtil.getLocalisation(valueKey, guild);

					spec.setTitle(local.getValue());
					spec.setDescription(flag.toString() + " " + valueLocal.getValue());
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
