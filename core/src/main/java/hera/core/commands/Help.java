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
		Localisation title = HeraUtil.getLocalisation(LocalisationKey.COMMAND_HELP, guild);
		return channel.createMessage(spec -> spec.setEmbed(embed -> {
			embed.setColor(Color.ORANGE);
			embed.setTitle(title.getValue());
			embed.setDescription(getHelp(guild));
		})).then();
	}

	private static String getHelp(Guild guild) {



		// NOTE this might be a problem later on (Integer compare)


		StringBuilder helpStringBuilder = new StringBuilder();
		for (String commandString : commandStrings) {
			helpStringBuilder.append(commandString + "\n");
		}

		return helpStringBuilder.toString();
	}

	private static Mono<Void> getEnabledCommands(Member member, Guild guild) {
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
				.doOnNext(permissions -> {
					List<Command> usableCommands = enabledCommands.stream()
						.filter(command -> HeraUtil.checkPermissions(command, permissions))
						.collect(Collectors.toList());

					// do whatever you want with these commands in here
				}).then();

		.map(cmd -> String.format("- %s", cmd.getName().name().toLowerCase()))

		member.getBasePermissions().flatMap(p );
}
}
