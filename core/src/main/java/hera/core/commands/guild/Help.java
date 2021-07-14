package hera.core.commands.guild;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.core.messages.formatter.list.ListGen;
import hera.database.entities.Command;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static hera.store.DataStore.STORE;

public class Help {

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Mono<String> message;
		String title;
		if (params.size() < 1) { //no text behind command
			title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP, guild).getValue();
			message = getHelpFromCommandList(getEnabledCommands(member, guild), guild);
		} else {
			String commandName = params.get(0);
			if (commandName.toUpperCase().equals("ALL")) { // help ALL, shows all commands
				title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP, guild).getValue();
				List<Command> commands = STORE.commands().getAll().stream().filter(cmd -> cmd.getLevel() < 2).collect(Collectors.toList());
				message = getHelpFromCommandList(Mono.just(commands), guild);
			} else { // help COMMAND_NAME, shows help to specific command
				Command cmd = HeraUtil.getNonOwnerCommandFromName(commandName, guild);
				if (cmd == null) { // input COMMAND_NAME not valid
					return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> {
						messageSpec.setDescription(String.format(HeraUtil.getLocalisation(LocalisationKey.ERROR_NOT_REAL_COMMAND, guild).getValue(), commandName));
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

	private static Mono<String> getHelpFromCommandList(Mono<List<Command>> commands, Guild guild) {
		Localisation noPermissionMsg = HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP_MISSING_PERMISSION, guild);
		return HeraUtil.getHeraPermissionSetForGuild(guild)
				.flatMap(heraPermissions -> commands.flatMap(cmnds -> {
						final StringBuilder helpStringBuilder = new StringBuilder();

						//sorting by name
						cmnds.sort(Comparator.comparing(c -> c.getName().toString().toLowerCase()));

						helpStringBuilder.append(new ListGen<Command>()
								.setNodes(" - %s$$_(%s)_$$")
								.setItems(cmnds)
								.addItemConverter(c -> c.getName().toString().toLowerCase())
								.addItemConverter(c -> noPermissionMsg.getValue())
								.makeList());

						return Mono.just(helpStringBuilder.toString());
				})
		);
	}


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
	}
}
