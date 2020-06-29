package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageSpec;
import hera.core.messages.MessageHandler;
import hera.database.entities.Command;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static hera.store.DataStore.STORE;

public class Help {

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Mono<String> message;
		String title;
		String commandName = params.get(0);
		if (commandName.isEmpty()) {
			title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP, guild).getValue();
			message = getHelpFromCommandList(getEnabledCommands(member, guild), guild);
		} else if (commandName.toUpperCase().equals("ALL")) {
			title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP, guild).getValue();
			List<Command> commands = STORE.commands().getAll().stream().filter(cmd -> cmd.getLevel() < 2).collect(Collectors.toList());
			message = getHelpFromCommandList(Mono.just(commands), guild);
		} else {
			Command cmd = getHelp(commandName);
			if (cmd == null) {
				return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> {
					messageSpec.setDescription(String.format(HeraUtil.getLocalisation(LocalisationKey.ERROR_NOT_REAL_COMMAND, guild).getValue(), commandName));
				})).then();
			}
			title = cmd.getName().toString().toLowerCase();
			//this will break as soon as descriptions are localized
			message = Mono.just(cmd.getDescription());
		}
		return message.flatMap(m -> MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
			messageSpec.setTitle(title);
			messageSpec.setDescription(m);
		}))).then();
	}

	private static Mono<String> getHelpFromCommandList(Mono<List<Command>> commands, Guild guild) {
		return HeraUtil.getHeraPermissionSetForGuild(guild)
				.flatMap(heraPermissions -> commands.flatMap(cmnds -> {
						final StringBuilder helpStringBuilder = new StringBuilder();

						cmnds.sort((commandA, commandB) -> {
							String a = commandA.getName().toString().toLowerCase();
							String b = commandB.getName().toString().toLowerCase();

							return a.compareTo(b);
						});

						cmnds.forEach(cmd -> {
							helpStringBuilder.append("- ");
							helpStringBuilder.append(cmd.getName().toString().toLowerCase());

							if (!HeraUtil.checkCommandPermissions(cmd, heraPermissions)) {
								helpStringBuilder.append(" (Disabled because Hera is missing Permissions)");
							}

							helpStringBuilder.append("\n");
						});

						return Mono.just(helpStringBuilder.toString());
				})
		);
	}

	private static Command getHelp(String commandName) {
		List<Command> commands =  STORE.commands().forName(commandName).stream().filter(cmd -> cmd.getLevel() < 2).collect(Collectors.toList());
		if (commands.isEmpty()) {
			return null;
		} else {
			return commands.get(0);
		}
	}

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
}
}
