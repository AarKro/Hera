package hera.core.util;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Permission;
import discord4j.rest.util.PermissionSet;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Command;
import hera.database.entities.Localisation;
import hera.database.entities.ModuleSettings;
import hera.database.entities.Role;
import net.bytebuddy.asm.Advice;
import reactor.core.publisher.Mono;

import java.time.Month;
import java.util.List;

import static hera.store.DataStore.STORE;
import static hera.core.util.FallbackLocalization.*;

public class PermissionUtil {
	public static Mono<PermissionSet> getHeraPermissionSetForGuild(GatewayDiscordClient client, Guild guild) {
		return client.getSelf().flatMap(user -> user.asMember(guild.getId()).flatMap(Member::getBasePermissions));
	}

	public static Mono<Boolean> checkHeraPermissions(GatewayDiscordClient client, Command command, Guild guild) {
		return getHeraPermissionSetForGuild(client, guild)
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
		if ( STORE.owners().isOwner(member.getId().asLong())) return Mono.just(true);

		if (CommandUtil.isCommandEnabled(command, guild)) {
			if (command.getLevel() > 1) { // if its a owner level command return false since
				return Mono.just(false);
			} else {
				return canUserCallCommand(command, member).filter(e -> e)
						.flatMap(exist -> {
							if (exist) return Mono.just(true);
							return MessageHandler
									.send(channel, MessageSpec.getErrorSpec(messageSpec -> messageSpec.setDescription(LOCALISATION_PERMISSION_ERROR.getValue())))
									.flatMap(message -> Mono.just(false));
						});
			}
		} else {
			return Mono.just(false);
		}
	}

	private static Mono<Boolean> canUserCallCommand(Command command, Member member) {
		if (command.getLevel() > 1) {
			return Mono.just(false);
		} else {
			//TODO check if there is a custom role system for the server and check for it

			//if it's an admin command and the user is no administrator return false else return true
			return member.getBasePermissions().flatMap(permissions ->isPermissionLevel(command.getLevel(), member));
		}
	}

	private static Mono<Boolean> isPermissionLevel(int level, Member member) {
		if (level > 1) {
			return Mono.just(STORE.owners().isOwner(member.getId().asLong()));
		} else if (level == 1) {
			return member.getBasePermissions().flatMap(permissions -> Mono.just(permissions.contains(Permission.ADMINISTRATOR)));
		} else {
			return Mono.just(true);
		}
	}

}
