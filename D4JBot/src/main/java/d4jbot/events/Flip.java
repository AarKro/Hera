package d4jbot.events;

import java.util.Random;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
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
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "flip")) {
			Random rng = new Random();
			String result = "";
			
			result = (rng.nextBoolean()) ? "Heads" : "Tails"; 

			ms.sendMessage(e.getChannel(), result);
		}
	}
}
