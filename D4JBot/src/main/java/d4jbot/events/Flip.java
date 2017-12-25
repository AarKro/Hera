package d4jbot.events;

import java.util.Random;

import d4jbot.misc.MessageSender;
import d4jbots.enums.BotPrefix;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Flip {

	private MessageSender ms;
	
	// default constructor
	public Flip() { }
	
	// constructor
	public Flip(MessageSender ms) {
		this.ms = ms;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "flip")) {
			Random rng = new Random();
			String result = "";
			
			result = (rng.nextBoolean()) ? "Heads" : "Tails"; 

			ms.sendMessage(e.getChannel(), true, result);
		}
	}
}
