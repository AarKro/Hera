package hera.core;

import discord4j.core.DiscordClient;
import discord4j.core.object.entity.*;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import discord4j.core.util.PermissionUtil;
import hera.core.messages.MessageSpec;
import hera.core.messages.MessageHandler;
import hera.database.entities.*;
import hera.database.types.GuildSettingKey;
import hera.database.types.LocalisationKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hera.store.DataStore.STORE;

public class HeraUtil {
	public static final Logger LOG = LoggerFactory.getLogger(HeraUtil.class);

	public static final Localisation LOCALISATION_GENERAL_ERROR = new Localisation("en", LocalisationKey.ERROR, "Seems like something went wrong... please try again");
	public static final Localisation LOCALISATION_PERMISSION_ERROR = new Localisation("en", LocalisationKey.ERROR, "You do not have the necessary permissions to use this command");
	public static final Localisation LOCALISATION_PARAM_ERROR = new Localisation("en", LocalisationKey.ERROR, "Command was not used correctly");

	private static DiscordClient client;

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

	public static Mono<PermissionSet> getHeraPermissionSetForGuild(Guild guild) {
		return client.getSelf().flatMap(user -> user.asMember(guild.getId()).flatMap(Member::getBasePermissions));
	}

	public static Mono<Boolean> checkHeraPermissions(Command command, Guild guild) {
		return getHeraPermissionSetForGuild(guild)
			.flatMap(heraPermissions -> {
				PermissionSet minPermissions = PermissionSet.of(command.getMinPermission());
				return Mono.just(heraPermissions.asEnumSet().containsAll(minPermissions.asEnumSet()));
			}
		);
	}

	public static boolean checkCommandPermissions(Command command, PermissionSet permissionSet) {
		PermissionSet minPermissions = PermissionSet.of(command.getMinPermission());
		return permissionSet.asEnumSet().containsAll(minPermissions.asEnumSet());
	}


	//TODO make this multiple methods
	public static Mono<Boolean> checkPermissions(Command command, Member member, Guild guild, MessageChannel channel) {
		if (STORE.owners().isOwner(member.getId().asLong())) return Mono.just(true);

		List<ModuleSettings> msList = STORE.moduleSettings().forModule(guild.getId().asLong(), command);
		ModuleSettings ms = !msList.isEmpty() ? msList.get(0) : null;
		if (ms == null || ms.isEnabled()) {
			if (command.getLevel() > 1) {
				return Mono.just(false);
			} else if (command.getLevel() == 1) {
				return member.getBasePermissions()
						.filter(permissions -> permissions.contains(Permission.ADMINISTRATOR))
						.hasElement()
						.flatMap(exist -> {
							if (exist) return Mono.just(true);
							return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> messageSpec.setDescription(LOCALISATION_PERMISSION_ERROR.getValue())))
									.flatMap(message -> Mono.just(false));
						});
			} else {
				return Mono.just(true);
			}
		} else {
			return Mono.just(false);
		}
	}

	// doesn't check for owner
	public static Boolean checkPermissions(Command command, PermissionSet permissions) {
		if (command.getLevel() > 1) {
			return false;
		} else if (command.getLevel() == 1) {
			return permissions.contains(Permission.ADMINISTRATOR);
		} else {
			return true;
		}
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
			} else if (params - expected > optional) {
				return false;
			}
		}
		return true;
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

	public static Localisation getLocalisation(LocalisationKey key, Guild guild) {
		List<GuildSetting> settings = STORE.guildSettings().forGuildAndKey(guild.getId().asLong(), GuildSettingKey.LANGUAGE);

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

	public static void setClient(DiscordClient _client) {
		client = _client;
	}

	public static DiscordClient getClient() {
		return client;
	}

	public static Mono<Boolean> hasRightsToSetRole(Guild guild, Role role) {
		return hasSetRoleRights(guild)
				.flatMap(hasSetRole -> hasHigherRole(guild, role)
						.flatMap(hasHigherRole -> Mono.just(hasSetRole && hasHigherRole))
				);
	}

	public static Mono<Boolean> hasSetRoleRights(Guild guild) {
		return client.getSelf()
				.flatMap(user -> user.asMember(guild.getId())
						.flatMap(m -> m.getRoles().any(p -> p.getPermissions().asEnumSet().contains(Permission.MANAGE_ROLES)))
				);
	}

	public static Mono<Boolean> hasHigherRole(Guild guild, Role role) {
		return client.getSelf()
				.flatMap(user -> user.asMember(guild.getId())
						.flatMap(m -> m.getHighestRole()
								.filterWhen(r -> Mono.just(r.getRawPosition() > role.getRawPosition()))
								.hasElement()
						)
				);
	}

	public static boolean isUserMention(String string) {
		return string.matches("<@!\\d{1,50}>");
	}

	public static Long getIdUserFromString(String mention) {
		return Long.parseLong(mention.substring(3, mention.length()-1));
	}

	public static Mono<Member> getUserFromMention(Guild guild, String mention) {
		if (isUserMention(mention)) {
			return guild.getMemberById(Snowflake.of(getIdUserFromString(mention))).onErrorResume(throwable -> {
				LOG.error("Error when trying to parse supplied user id: {}", mention);
				LOG.error("Stacktrace", throwable);
				return Mono.empty();
			});
		}
		return Mono.empty();
	}

	public static boolean isRoleMention(String string) {
		return string.matches("<@&\\d{1,50}>");
	}

	public static Long getRoleIdFromString(String mention) {
		return Long.parseLong(mention.substring(3, mention.length()-1));
	}

	public static Mono<Role> getRoleFromMention(Guild guild, String mention) {
		if (isRoleMention(mention)) {
			return guild.getRoleById(Snowflake.of(getRoleIdFromString(mention))).onErrorResume(throwable -> {
				LOG.error("Error when trying to parse supplied role id: {}", mention);
				LOG.error("Stacktrace", throwable);
				return Mono.empty();
			});
		}
		return Mono.empty();
	}

	public static boolean isChannelMention(String string) {
		return string.matches("<#\\d{1,50}>");
	}

	public static Long getIdChannelFromString(String mention) {
		return Long.parseLong(mention.substring(2, mention.length()-1));
	}

	public static Mono<GuildChannel> getChannelFromMention(Guild guild, String mention) {
		if (isChannelMention(mention)) {
			return guild.getChannelById(Snowflake.of(getIdChannelFromString(mention))).onErrorResume(throwable -> {
				LOG.error("Error when trying to parse supplied channel id: {}", mention);
				LOG.error("Stacktrace", throwable);
				return Mono.empty();
			});
		}
		return Mono.empty();
	}
}
