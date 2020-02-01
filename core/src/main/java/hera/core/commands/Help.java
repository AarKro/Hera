package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.database.entities.Command;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
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
			message = getHelpFromCommandList(getEnabledCommands(member, guild));
		} else if (commandName.equals("all")) {
			title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP, guild).getValue();
			List<Command> commands = STORE.commands().getAll().stream().filter(cmd -> cmd.getLevel() < 2).collect(Collectors.toList());
			message = getHelpFromCommandList(Mono.just(commands));
		} else {
			Command cmd = getHelp(commandName);
			if (cmd == null) {
				
			}

		}


		Localisation title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP, guild);
		return channel.createMessage(spec -> spec.setEmbed(embed -> {
			embed.setColor(Color.ORANGE);
			embed.setTitle(title.getValue());
			embed.setDescription(getHelp(guild));
		})).then();
	}

	private static Mono<String> getHelpFromCommandList(Mono<List<Command>> commands) {
		return commands.flatMap(cmnds -> {
			StringBuilder helpStringBuilder = new StringBuilder();
			for (Command cmd : cmnds) {
				helpStringBuilder.append(cmd.getName() + "\n");
			}
			return Mono.just(helpStringBuilder.toString());
		});
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

		return member.getBasePermissions()
				.flatMap(permissions -> {
						return Mono.just(enabledCommands.stream()
						.filter(command -> HeraUtil.checkPermissions(command, permissions))
						.collect(Collectors.toList()));
				});
}
}
