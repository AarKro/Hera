package hera.core.util;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Permission;
import discord4j.rest.util.PermissionSet;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Command;
import reactor.core.publisher.Mono;

import static hera.core.util.LocalisationUtil.LOCALISATION_PERMISSION_ERROR;
import static hera.store.DataStore.STORE;

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

	public static Mono<Boolean> canUserCallCommand(Command command, Member member) {
		if (command.getLevel() > 1) {
			return Mono.just(false);
		} else {
			//TODO check if there is a custom role system for the server and check for it

			//if it's an admin command and the user is no administrator return false else return true
			return member.getBasePermissions().flatMap(permissions -> hasPermissionLevel(member, command.getLevel()));
		}
	}

	public static Mono<Boolean> hasPermissionLevel(Member member, int level) {
		return member.getBasePermissions().flatMap(permissions -> Mono.just(hasPermissionLevel(member.getId().asLong(), permissions, level)));
	}

	public static Boolean hasPermissionLevel(Long memberId, PermissionSet permissions, int level) {
		if (level > 1) {
			return STORE.owners().isOwner(memberId);
		} else if (level == 1) {
			return permissions.contains(Permission.ADMINISTRATOR);
		} else {
			return true;
		}
	}

	public static Mono<Boolean> canHeraSetRole(GatewayDiscordClient client, Guild guild, Role role) {
		return client.getSelf().flatMap(user -> user.asMember(guild.getId()).flatMap(member -> hasRightsToSetRole(member, role)));
	}

	public static Mono<Boolean> hasRightsToSetRole(Member member, Role role) {
		return hasManageRoleRights(member)
				.flatMap(canSetRoles -> hasHigherRole(member, role)
						.flatMap(hasHigherRole -> Mono.just(canSetRoles && hasHigherRole))
				);
	}

	public static Mono<Boolean> hasManageRoleRights(Member member) {
		return member.getBasePermissions().filter(permissions -> permissions.contains(Permission.MANAGE_ROLES)).hasElement();
	}

	public static Mono<Boolean> hasHigherRole(Member member, Role role) {
		//flatMaps the Flux<Role> to Flux<Boolean> containing if the role at said spot is higher than the base role.
		//then it checks if any of the values is true
		return member.getHighestRole().flatMap(mRole -> mRole.getPosition()
						.flatMap(mRolePos -> role.getPosition()
								.flatMap(rolePos -> Mono.just(mRolePos > rolePos))));

		/*
			might be better to do
			return member.getHighestRole().filter(r -> r.getRawPosition() > role.getRawPosition()).hasElement();
			or
			return member.getHighestRole().flatMap(r -> Mono.just(r.getRawPosition() > role.getRawPosition()));

			i feel like it is cleaner to get it the way i did it though since this way im sure it actually gets the data.
		*/
	}

}
