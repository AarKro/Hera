package hera.core;

import discord4j.core.object.entity.*;
import discord4j.core.object.util.Permission;
import hera.database.entities.mapped.Alias;
import hera.database.entities.mapped.Command;
import hera.database.entities.mapped.GuildSettings;
import hera.database.entities.mapped.Localisation;
import hera.database.types.GuildSettingKey;
import hera.database.types.LocalisationKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hera.store.DataStore.STORE;

public class HeraUtil {
	public static final Logger LOG = LoggerFactory.getLogger(HeraUtil.class);

	public static final Localisation LOCALISATION_GENERAL_ERROR = new Localisation("en", LocalisationKey.ERROR, "Seems like something went wrong... please try again");
	public static final Localisation LOCALISATION_PERMISSION_ERROR = new Localisation("en", LocalisationKey.ERROR, "You do not have the necessary permissions to use this command");
	public static final Localisation LOCALISATION_PARAM_ERROR = new Localisation("en", LocalisationKey.ERROR, "Command was not used correctly");

	public static Command getCommandFromMessage(String message, String prefix, Guild guild) {
		// message is a complete discord command. (prefix + command + parameters)
		String commandName = message.split(" ")[0].substring(prefix.length());
		List<Command> commands = STORE.commands().forName(commandName);
		if (commands.isEmpty()) {
			List<Alias> aliases = STORE.alias().forGuildAndAlias(guild.getId().asLong(), commandName);
			if (aliases.isEmpty()) {
				return null;
			} else {
				return STORE.commands().forId(aliases.get(0).getCommand()).get(0);
			}
		} else {
			return commands.get(0);
		}
	}

	public static Mono<Boolean> checkPermissions(Command command, Member member, Guild guild, MessageChannel channel) {
		// add modularisation stuff here (get module for guild + command from STORE and check permission for it)

		boolean isOwner = !STORE.owners().getAll().stream()
			.filter(owner -> owner.getUser().equals(member.getId().asLong()))
			.collect(Collectors.toList()).isEmpty();

		if (isOwner) return Mono.just(true);

		if (command.isAdmin()) {
			return member.getBasePermissions()
					.filter(permissions -> permissions.contains(Permission.ADMINISTRATOR))
					.hasElement()
					.flatMap(exist -> {
						if (exist) return Mono.just(true);
						return channel.createMessage(LOCALISATION_PERMISSION_ERROR.getValue()).flatMap(c ->  Mono.just(false));
					});
		}
		else {
			return Mono.just(true);
		}
	}

	public static Mono<Boolean> checkParameters(String message, Command command, MessageChannel channel) {
		return Mono.just(message.split(" ").length - 1 >= command.getParamCount())
		.flatMap(valid -> {
			if (valid) return Mono.just(true);
			else return channel.createMessage(LOCALISATION_PARAM_ERROR.getValue()).flatMap(c -> Mono.just(false));
		});
	}

	public static List<String> extractParameters(String message, Command command) {
		String[] parts = message.split(" ");
		List<String> params = new ArrayList<>();

		// start at index 1 so we skip the perfix + command
		for(int i = 1 ; i <= command.getParamCount(); i++) {
			params.add(parts[i]);
		}

		if (command.isInfiniteParam()) {
			StringBuilder multiPartParam = new StringBuilder();
			for (int i = command.getParamCount() + 1; i < parts.length; i++) {
				multiPartParam.append(" ");
				multiPartParam.append(parts[i]);
			}

			params.add(multiPartParam.toString().trim());
		}

		return params;
	}

	public static Localisation getLocalisation(LocalisationKey key, Guild guild) {
		List<GuildSettings> settings = STORE.guildSettings().forGuildAndKey(guild.getId().asLong(), GuildSettingKey.LANGUAGE);

		String language;
		if (settings.isEmpty()) language = "en";
		else language = settings.get(0).getValue();

		List<Localisation> messages;
		messages = STORE.localisations().forLanguageAndKey(language, key);

		if (messages.isEmpty() && !language.equals("en")) {
			LOG.debug("message for custom language '{}' not found, get standard english localisation instead", language);
			messages = STORE.localisations().forLanguageAndKey("en", key);
			if (messages.isEmpty()) {
				LOG.error("No localisation found for {} {}", language, key.name());
				return LOCALISATION_GENERAL_ERROR;
			}

			return messages.get(0);
		} else if (messages.isEmpty()) {
			LOG.error("No localisation found for {} {}", language, key.name());
			return LOCALISATION_GENERAL_ERROR;
		} else {
			return messages.get(0);
		}
	}

	public static Flux<Member> getDiscordUser(Guild guild, String user) {
		return guild.getMembers().filter(member -> member.getDisplayName().toUpperCase().equals(user.toUpperCase()));
	}

	public static Flux<Member> getDiscordUser(Guild guild, Long user) {
		return guild.getMembers().filter(member -> user.equals(member.getId().asLong()));
	}

	public static String getFormattedTime(long time) {
		long days = time / 1000 / 60 / 60 / 24;
		long hours = time / 1000 / 60 / 60 - (days * 24);
		long minutes = time / 1000 / 60 - (hours * 60 + (days * 24 * 60));
		long seconds = time / 1000 - (minutes * 60 + (hours * 60 * 60 + (days * 24 * 60 * 60)));

		if (days == 0 && hours == 0 && minutes == 0 && seconds == 0) return "0";

		String formatted = String.format("%sd %sh %sm %ss", days, hours, minutes, seconds);
		String[] parts = formatted.split(" ");

		StringBuilder builder = new StringBuilder();
		for (String part : parts) {
			if (builder.length() > 0 || part.charAt(0) != '0') {
				builder.append(part);
				builder.append(" ");
			}
		}

		return builder.toString().trim();
	}
}
