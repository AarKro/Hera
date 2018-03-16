package hera.events;

import hera.enums.BotSettings;
import hera.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Version implements Command {
	
	private static Version instance;
	
	public static Version getInstance() {
		if (instance == null) {
			instance = new Version();
		}
		return instance;
	}

	private MessageSender ms;
	
	// default constructor
	private Version() {
		this.ms = MessageSender.getInstance();
	}
	
	public void execute(MessageReceivedEvent e) {
			ms.sendMessage(e.getChannel(), BotSettings.BOT_VERSION.getPropertyValue());
	}
}
