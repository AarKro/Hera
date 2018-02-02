package d4jbot.events;

import d4jbot.enums.BotCommands;
import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Help implements Command {

	private static Help instance;
	
	public static Help getInstance() {
		if (instance == null) instance = new Help();
		return instance;
	}
	
	private MessageSender ms;
	
	private Help() { 
		this.ms = MessageSender.getInstance();
	}
	
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "help")) {
			
			String commands = "";
			for(BotCommands command : BotCommands.values()) {
				commands += "\n- " + command.getCommandName();
			}
			
			ms.sendMessage(e.getChannel(), "Available Commands:" + commands + "\nFor more information visit https://github.com/Chromeroni/Hera-Chatbot");
														 
		}
	}
}