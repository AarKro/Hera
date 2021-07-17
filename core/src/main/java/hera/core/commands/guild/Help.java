package hera.core.commands.guild;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.PermissionSet;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
<<<<<<< HEAD:core/src/main/java/hera/core/commands/guild/Help.java
import hera.core.messages.formatter.list.ListGen;
=======
import hera.core.util.CommandUtil;
import hera.core.util.PermissionUtil;
import hera.database.entities.Alias;
>>>>>>> split HeraUtil. Commands still need to be changed:core/src/main/java/hera/core/commands/Help.java
import hera.database.entities.Command;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static hera.core.util.CommandUtil.getEnabledCommands;
import static hera.core.util.CommandUtil.getNonOwnerCommandFromName;
import static hera.core.util.LocalisationUtil.getLocalisation;
import static hera.core.util.PermissionUtil.*;
import static hera.store.DataStore.STORE;

public class Help {

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Mono<String> message;
		String title;
		GatewayDiscordClient client = event.getClient();
		if (params.size() < 1) { //no text behind command
			title = getLocalisation(LocalisationKey.COMMAND_HELP, guild).getValue();
			message = getHelpFromCommandList(event.getClient(), getVisibleCommands(event.getClient(), guild, member), guild);
		} else {
			String commandName = params.get(0);
			if (commandName.toUpperCase().equals("ALL")) { // help ALL, shows all commands
				title = getLocalisation(LocalisationKey.COMMAND_HELP, guild).getValue();
				List<Command> commands = STORE.commands().getAll().stream().filter(cmd -> cmd.getLevel() < 2).collect(Collectors.toList());
				message = getHelpFromCommandList(event.getClient(), Mono.just(commands), guild);
			} else { // help COMMAND_NAME, shows help to specific command
				Command cmd = getNonOwnerCommandFromName(commandName, guild);
				if (cmd == null) { // input COMMAND_NAME not valid
					return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> {
						messageSpec.setDescription(String.format(getLocalisation(LocalisationKey.ERROR_NOT_REAL_COMMAND, guild).getValue(), commandName));
					})).then();
				}
				title = cmd.getName().toString().toLowerCase();
				message = Mono.just(cmd.getDescription().getValue());
			}
		}
		return message.flatMap(m -> MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
			messageSpec.setTitle(title);
			messageSpec.setDescription(m);
		}))).then();
	}

	private static Mono<String> getHelpFromCommandList(GatewayDiscordClient client, Mono<List<Command>> commands, Guild guild) {
		Localisation noPermissionMsg = getLocalisation(LocalisationKey.COMMAND_HELP_MISSING_PERMISSION, guild);
		return getHeraPermissionSetForGuild(client, guild)
				.flatMap(heraPermissions -> commands.flatMap(cmnds -> {
						final StringBuilder helpStringBuilder = new StringBuilder();

						//sorting by name
						cmnds.sort(Comparator.comparing(c -> c.getName().toString().toLowerCase()));

<<<<<<< HEAD:core/src/main/java/hera/core/commands/guild/Help.java
						helpStringBuilder.append(new ListGen<Command>()
								.setNodes(" - %s$$_(%s)_$$")
								.setItems(cmnds)
								.addItemConverter(c -> c.getName().toString().toLowerCase())
								.addItemConverter(c -> noPermissionMsg.getValue())
								.makeList());
=======
							return a.compareTo(b);
						});

						cmnds.forEach(cmd -> {
							helpStringBuilder.append("- ");
							helpStringBuilder.append(cmd.getName().toString().toLowerCase());

							//TODO check if this is faster than concat so "_(" + noPermissionMsg.getValue() + ")_"
							if (!checkCommandPermissions(cmd, heraPermissions)) {
								helpStringBuilder.append(" _(");
								helpStringBuilder.append(noPermissionMsg.getValue());
								helpStringBuilder.append(")_ ");
							}

							helpStringBuilder.append("\n");
						});
>>>>>>> split HeraUtil. Commands still need to be changed:core/src/main/java/hera/core/commands/Help.java

						return Mono.just(helpStringBuilder.toString());
				})
		);
	}

<<<<<<< HEAD:core/src/main/java/hera/core/commands/guild/Help.java

	//TODO get this to another class
	private static Mono<List<Command>> getEnabledCommands(Member member, Guild guild) {
		List<Long> disabledCommands = STORE.moduleSettings().forGuild(guild.getId().asLong()).stream()
				.filter(ms -> !ms.isEnabled())
				.map(ms -> ms.getCommand().getId())
				.collect(Collectors.toList());

		List<Command> allCommands = STORE.commands().getAll();

		List<Command> enabledCommands = allCommands.stream()
				.filter(cmd -> !disabledCommands.contains(cmd.getId()))
				.filter(cmd -> cmd.getLevel() < 2)
				.collect(Collectors.toList());

		return HeraUtil.getHeraPermissionSetForGuild(guild)
				.flatMap(heraPermissionSet -> member.getBasePermissions()
						.flatMap(permissions -> Mono.just(enabledCommands.stream()
								.filter(command -> HeraUtil.checkCommandPermissions(command, heraPermissionSet))
								.filter(command -> HeraUtil.checkPermissions(command, permissions))
								.collect(Collectors.toList()))
						)
				);
=======
	private static Mono<List<Command>> getVisibleCommands(GatewayDiscordClient client, Guild guild, Member member) {
		List<Command> enabledCommands = getEnabledCommands(client, guild);

		return member.getBasePermissions()
				.flatMap(permissions -> Mono.just(enabledCommands.stream().
						filter(cmd -> hasPermissionLevel(member.getId().asLong(), permissions, cmd.getLevel())).collect(Collectors.toList()))
		);
>>>>>>> split HeraUtil. Commands still need to be changed:core/src/main/java/hera/core/commands/Help.java
	}
}
