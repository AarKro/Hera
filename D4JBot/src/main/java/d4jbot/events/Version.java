package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import sx.blah.discord.api.events.EventSubscriber;
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
	
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "version")) {
			ms.sendMessage(e.getChannel(), BotSettings.BOT_VERSION.getPropertyValue());
		}
	}
}
