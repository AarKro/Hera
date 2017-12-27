package d4jbot.events;

import d4jbot.misc.MessageSender;
import d4jbots.enums.BotPrefix;
import d4jbots.enums.BotVersion;
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
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "version")) {
			ms.sendMessage(e.getChannel(), true, BotVersion.VERSION.getVersion());
		}
	}
}
