package d4jbot.events;

import d4jbot.misc.MessageSender;
import d4jbots.enums.BotPrefix;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Help {

	// default constructor
	public Help() { }
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "help")) {
			MessageSender.sendMessage(e.getChannel(), true, "Available Commands:"
														 +"\n- bind"
													  	 +"\n- report"
														 +"\n- flip"
														 +"\n- teams"
														 +"\n- help");
		}
	}
}
