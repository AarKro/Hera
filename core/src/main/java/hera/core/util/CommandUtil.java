package hera.core.util;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Alias;
import hera.database.entities.Command;
import hera.database.entities.ModuleSettings;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hera.core.util.LocalisationUtil.LOCALISATION_PARAM_ERROR;
import static hera.store.DataStore.STORE;

public class CommandUtil {
	public static Boolean isCommandEnabled(Command command, Guild guild) {
		List<ModuleSettings> msList = STORE.moduleSettings().forModule(guild.getId().asLong(), command);
		ModuleSettings ms = !msList.isEmpty() ? msList.get(0) : null;
		return ms == null || ms.isEnabled();
	}

	private static List<Long> getDisabledCommandsIds(Guild guild) {
		return STORE.moduleSettings().forGuild(guild.getId().asLong()).stream()
				.filter(ms -> !ms.isEnabled())
				.map(ms -> ms.getCommand().getId())
				.collect(Collectors.toList());
	}

	public static List<Command> getEnabledCommands(GatewayDiscordClient client, Guild guild) {
		List<Long> disabledCommandsIds = getDisabledCommandsIds(guild);

		List<Command> commands = STORE.commands().getAll();

		commands.removeIf(cmd -> disabledCommandsIds.contains(cmd));

		return commands;
	}

	public static Command getCommandFromMessage(String message, String prefix, Guild guild) {
		// message is a complete discord command. (prefix + command + parameters)
		String commandName = message.split(" ")[0].substring(prefix.length());
		List<Command> commands = STORE.commands().forName(commandName);
		if (commands.isEmpty()) {
			List<Alias> aliases = STORE.aliases().forGuildAndAlias(guild.getId().asLong(), commandName);
			if (aliases.isEmpty()) {
				return null;
			} else {
				return STORE.commands().get(aliases.get(0).getCommand().getId());
			}
		} else {
			return commands.get(0);
		}
	}

	public static Command getCommandFromName(String commandName, Guild guild) {
		List<Command> commands =  STORE.commands().forName(commandName).stream().collect(Collectors.toList());
		if (commands.isEmpty()) {
			List<Alias> aliases = STORE.aliases().forGuildAndAlias(guild.getId().asLong(), commandName);
			if (!aliases.isEmpty()) {
				aliases.get(0).getCommand();
			}
			return null;
		} else {
			return commands.get(0);
		}
	}

	public static Command getNonOwnerCommandFromName(String commandName, Guild guild) {
		Command out = getCommandFromName(commandName, guild);
		return out.getLevel() < 2 ? out : null;
	}

	public static Mono<Boolean> checkParameters(String message, Command command, MessageChannel channel) {
		return Mono.just(checkParamAmount(message.split(" ").length - 1, command.getParamCount(), command.getOptionalParams()))
				.flatMap(valid -> {
					if (valid) return Mono.just(true);
					else return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> messageSpec.setDescription(LOCALISATION_PARAM_ERROR.getValue())))
							.flatMap(c -> Mono.just(false));
				});
	}

	public static boolean checkParamAmount(int params, int expected, int optional) {
		if (params < expected) {
			return false;
		} else {
			if (optional == -1) {
				return true;
			} else return params - expected <= optional;
		}
	}

	public static List<String> extractParameters(String message, Command command) {
		String[] parts = message.trim().split(" ");
		List<String> params = new ArrayList<>();

		// start at index 1 so we skip the prefix + command
		for(int i = 1 ; i < parts.length; i++) {
			params.add(parts[i]);
		}

		return params;
	}
}
