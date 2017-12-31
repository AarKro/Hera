package d4jbot.events;

import d4jbot.misc.MessageSender;
import d4jbots.enums.BotPrefix;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Help {

	private MessageSender ms;
	
	// default constructor
	public Help() { }
	
	// constructor
	public Help(MessageSender ms) {
		this.ms = ms;
	}
		
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "help")) {
			ms.sendMessage(e.getChannel(), true, "Available Commands:"
														 +"\n- bind"
													  	 +"\n- report"
														 +"\n- flip"
														 +"\n- teams"
														 +"\n- vote"
														 +"\n- y"
														 +"\n- n"
														 +"\n- end"
														 +"\n- version"
														 +"\n- begone"
														 +"\n- help"
														 +"\n- motd"
														 +"\nFor more information visit https://github.com/Chromeroni/D4JBot");
		}
	}
}
