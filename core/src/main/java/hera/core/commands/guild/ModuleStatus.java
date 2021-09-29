package hera.core.commands.guild;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Command;
import hera.database.entities.ModuleSettings;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static hera.store.DataStore.STORE;

public class ModuleStatus {
	private static class CommandStatus {
		protected Command command;
		protected boolean isEnabled;
		protected boolean hasPermissions;
	}

	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Mono<String> message;
		String title;
		Mono<List<CommandStatus>> commandStatusMap = makeCommandStatusMap(getCommands(), guild);
		if (params.size() < 1) {
			title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_MODULESTATUS_TITLE_ALL, guild).getValue();
			message = makeMessageForAllCommands(commandStatusMap, guild);
		} else {
			String commandName = params.get(0);
			//TODO add localization option option so that if you write Modules igschaltä it can work / talk to erin bout it
			if (commandName.toUpperCase().equals("ON")) {
				title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_MODULESTATUS_TITLE_ON, guild).getValue();
				message = makeMessageForFilteredByEnabledCommands(commandStatusMap, true,  guild);
			} else if (commandName.toUpperCase().equals("OFF")) {
				title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_MODULESTATUS_TITLE_OFF, guild).getValue();
				message = makeMessageForFilteredByEnabledCommands(commandStatusMap, false, guild);
			} else if (commandName.toUpperCase().equals("GROUPED")) {
				title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_MODULESTATUS_TITLE_GROUPED, guild).getValue();
				message = makeMessageForAllCommandsGrouped(commandStatusMap, guild);
			} else if (commandName.toUpperCase().equals("ALL")) {
				title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_MODULESTATUS_TITLE_ALL, guild).getValue();
				message = makeMessageForAllCommands(commandStatusMap, guild);
			} else {
				Command cmd = HeraUtil.getNonOwnerCommandFromName(commandName, guild);
				if (cmd == null) { // input COMMAND_NAME not valid
					return MessageHandler.send(channel, MessageSpec.getErrorSpec(messageSpec -> {
						messageSpec.setDescription(String.format(HeraUtil.getLocalisation(LocalisationKey.ERROR_NOT_REAL_COMMAND, guild).getValue(), commandName));
					})).then();
				}
				title = cmd.getName().toString().toLowerCase();
				List<ModuleSettings> moduleSettings = STORE.moduleSettings().forModule(guild.getId().asLong(), cmd);
				boolean isEnabled = moduleSettings.isEmpty() ? true : moduleSettings.get(0).isEnabled();
				String isEnabledFormat = getMessageForEnabled(isEnabled, guild);
				message = Mono.just(String.format(isEnabledFormat, cmd.getName().name().toLowerCase()));
			}
		}

		return message.flatMap(m -> MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> {
			messageSpec.setTitle(title);
			messageSpec.setDescription(m);
		}))).then();
	}

	private static Mono<String> makeMessageForAllCommands(Mono<List<CommandStatus>> commandStatuses, Guild guild) {
		return commandStatuses.flatMap(commandStatus -> {
			StringBuilder sb = new StringBuilder();
			List<CommandStatus> cmdsSorted = commandStatus.stream().sorted(Comparator.comparing(e -> e.command.getName())).collect(Collectors.toList());
			for (CommandStatus cmd : cmdsSorted) {

				sb.append(String.format(" - %s: %s", cmd.command.getName().name().toLowerCase(), getTextForEnabled(cmd.isEnabled, guild)));
				if (!cmd.hasPermissions) {
					sb.append(" _(");
					sb.append(HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP_MISSING_PERMISSION, guild).getValue());
					sb.append(")_");
				}
				sb.append("\n");
			}
			return Mono.just(sb.toString());
		});
	}


	private static Mono<String> makeMessageForAllCommandsGrouped(Mono<List<CommandStatus>> commandStatuses, Guild guild) {
		return commandStatuses.flatMap(commandStatus -> {
			StringBuilder sb = new StringBuilder();
			List<CommandStatus> onCommands = commandStatus.stream().filter(e -> e.isEnabled).sorted(Comparator.comparing(e -> e.command.getName())).collect(Collectors.toList());
			List<CommandStatus> offCommands = commandStatus.stream().filter(e -> !e.isEnabled).sorted(Comparator.comparing(e -> e.command.getName())).collect(Collectors.toList());
			sb.append("**");
			sb.append(getTextForEnabled(true, guild));
			sb.append("**: \n");
			for (CommandStatus cmd : onCommands) {
				sb.append(String.format(" - "));
				sb.append(cmd.command.getName().name().toLowerCase());
				if (!cmd.hasPermissions) {
					sb.append(" _(");
					sb.append(HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP_MISSING_PERMISSION, guild).getValue());
					sb.append(")_");
				}
				sb.append("\n");
			}
			sb.append("\n");
			sb.append("**");
			sb.append(getTextForEnabled(false, guild));
			sb.append("**: \n");
			for (CommandStatus cmd : offCommands) {
				sb.append(String.format(" - %s", cmd.command.getName().name().toLowerCase()));
				if (!cmd.hasPermissions) {
					sb.append(" _(");
					sb.append(HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP_MISSING_PERMISSION, guild).getValue());
					sb.append(")_");
				}
				sb.append("\n");
			}
			return Mono.just(sb.toString());
		});
	}

	private static Mono<String> makeMessageForFilteredByEnabledCommands(Mono<List<CommandStatus>> commandStatuses, boolean filterIf, Guild guild) {
		return commandStatuses.flatMap(commandStatus -> {
			StringBuilder sb = new StringBuilder();
			List<CommandStatus> commands = commandStatus.stream().filter(e -> e.isEnabled == filterIf).sorted(Comparator.comparing(e -> e.command.getName())).collect(Collectors.toList());
			for (CommandStatus cmd : commands) {
				sb.append(String.format(" - %s", cmd.command.getName().name().toLowerCase()));
				if (!cmd.hasPermissions) {
					sb.append(" _(");
					sb.append(HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP_MISSING_PERMISSION, guild).getValue());
					sb.append(")_");
				}
				sb.append("\n");
			}
			return Mono.just(sb.toString());
		});
	}

	private static Mono<List<CommandStatus>> makeCommandStatusMap(List<Command> commands, Guild guild) {
		return HeraUtil.getHeraPermissionSetForGuild(guild)
			.flatMap(heraPermissions ->  {
				List<CommandStatus> commandList = new ArrayList<>();

				for (Command cmd : commands) {
					CommandStatus status = new CommandStatus();

					List<ModuleSettings> moduleSettings = STORE.moduleSettings().forModule(guild.getId().asLong(), cmd);
					status.command = cmd;
					status.isEnabled = moduleSettings.isEmpty() ? true : moduleSettings.get(0).isEnabled();
					status.hasPermissions = HeraUtil.checkCommandPermissions(cmd, heraPermissions);

					commandList.add(status);
				}

				return Mono.just(commandList);
			}
		);
	}


	private static List<Command> getCommands() {

		List<Command> allCommands = STORE.commands().getAll();

		return allCommands.stream()
				.filter(cmd -> cmd.getLevel() < 2)
				.collect(Collectors.toList());

	}


	private static String getTextForEnabled(boolean enabled, Guild guild) {
		return enabled ? HeraUtil.getLocalisation(LocalisationKey.COMMAND_MODULESTATUS_ENABLED, guild).getValue() : HeraUtil.getLocalisation(LocalisationKey.COMMAND_MODULESTATUS_DISABLED, guild).getValue();
	}

	private static String getMessageForEnabled(boolean enabled, Guild guild) {
		return enabled ? HeraUtil.getLocalisation(LocalisationKey.COMMAND_MODULESTATUS_COMMAND_ENABLED, guild).getValue() : HeraUtil.getLocalisation(LocalisationKey.COMMAND_MODULESTATUS_COMMAND_DISABLED, guild).getValue();
	}
}
