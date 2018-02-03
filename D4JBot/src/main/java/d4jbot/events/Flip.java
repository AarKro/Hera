package d4jbot.events;

import java.util.Random;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Flip implements Command {

	private static Flip instance;
	
	public static Flip getInstance() {
		if (instance == null) instance = new Flip();
		return instance;
	}
	
	private MessageSender ms;
	
	// default constructor
	private Flip() {
		this.ms = MessageSender.getInstance();
	}
	
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "flip")) {
			Random rng = new Random();
			String result = "";
			
			result = (rng.nextBoolean()) ? "Heads" : "Tails"; 

			ms.sendMessage(e.getChannel(), result);
		}
	}
}
