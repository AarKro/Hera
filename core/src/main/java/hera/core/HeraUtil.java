package hera.core;

import discord4j.core.object.entity.*;
import discord4j.core.object.util.Permission;
import hera.database.entities.mapped.Command;
import hera.database.entities.mapped.GuildSettings;
import hera.database.entities.mapped.Localisation;
import hera.database.types.GuildSettingKey;
import hera.database.types.LocalisationKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static hera.store.DataStore.STORE;

public class HeraUtil {
	public static final Logger LOG = LoggerFactory.getLogger(HeraUtil.class);

	private static final Localisation LOCALISATION_GENERAL_ERROR = new Localisation("en", LocalisationKey.ERROR, "Seems like something went wrong... please try again");
	private static final Localisation LOCALISATION_PERMISSION_ERROR = new Localisation("en", LocalisationKey.ERROR, "You do not have the necessary permissions to use this command");
	private static final Localisation LOCALISATION_PARAM_ERROR = new Localisation("en", LocalisationKey.ERROR, "Command was not used correctly");

	public static Command getCommandFromMessage(String message, String prefix, Guild guild) {
		if (message.length() < prefix.length()) return null;
		// Add alias stuff here
		// message is a complete discord command. (prefix + command + parameters)
		List<Command> commands = STORE.commands().forName(message.split(" ")[0].substring(prefix.length()));
		if (commands.isEmpty()) return null;
		else return commands.get(0);
	}

	public static Mono<Boolean> checkPermissions(Command command, Member member, Guild guild, MessageChannel channel) {
		// add modularisation stuff here (get module for guild + command from STORE and check permission for it)
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
		String[] parts = message.split("");
		List<String> params = new ArrayList<>();

		int i = 1; // start at index 1 so we skip the perfix + command
		while(i <= command.getParamCount()) {
			params.add(parts[i]);
			i++;
		}

		if (command.isInfiniteParam()) {
			StringBuilder multiPartParam = new StringBuilder();
			for (int j = i; j < parts.length; j++) {
				multiPartParam.append(" ");
				multiPartParam.append(parts[j]);
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
}
