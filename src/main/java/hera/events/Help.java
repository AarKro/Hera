package hera.events;

import hera.enums.BotCommands;
import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Help implements Command {

	private static Help instance;

	public static Help getInstance() {
		if (instance == null)
			instance = new Help();
		return instance;
	}

	private MessageSender ms;

	private Help() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		String commands = "";
		for (BotCommands command : BotCommands.values()) {
			commands += "\n- " + command.getCommandName();
		}

		ms.sendMessage(e.getChannel(), "Available Commands:", commands
				+ "\nFor more information visit https://github.com/Chromeroni/Hera-Chatbot#commands");
	}
}