package hera.events.commands;

import hera.enums.BotCommands;
import hera.events.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Help extends Command {

	private MessageSender ms;

	Help() {
		super(null, 0, false);
		this.ms = MessageSender.getInstance();
	}


	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		String commands = "";
		for (BotCommands command : BotCommands.values()) {
			commands += "\n- " + command.getCommandName();
		}

		ms.sendMessage(e.getChannel(), "Available Commands:", commands
				+ "\n\nFor more information visit https://chromeroni.github.io/Hera-Chatbot/");
	}
}