package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
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
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "help")) {
			ms.sendMessage(e.getChannel(), true, "Available Commands:"
														 +"\n- bind"
													  	 +"\n- report"
														 +"\n- flip"
														 +"\n- teams"
														 +"\n- vote"
														 +"\n- yes"
														 +"\n- no"
														 +"\n- end"
														 +"\n- version"
														 +"\n- begone"
														 +"\n- help"
														 +"\n- motd"
														 +"\n- join"
														 +"\n- leave"
														 +"\n- np"
														 +"\n- volume"
														 +"\n- play"
														 +"\n- queue"
														 +"\nFor more information visit https://github.com/Chromeroni/D4JBot");
		}
	}
}
