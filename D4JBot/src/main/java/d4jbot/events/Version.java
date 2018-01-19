package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Version {

	private MessageSender ms;
	
	// default constructor
	public Version() { }
	
	// constructor
	public Version(MessageSender ms) {
		this.ms = ms;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "version")) {
			ms.sendMessage(e.getChannel(), true, BotSettings.BOT_VERSION.getPropertyValue());
		}
	}
}
