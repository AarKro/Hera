package hera.events;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Flip implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Flip.class);
	
	private static Flip instance;

	public static Flip getInstance() {
		if (instance == null)
			instance = new Flip();
		return instance;
	}

	private MessageSender ms;

	// default constructor
	private Flip() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Flip.execute");
		Random rng = new Random();
		String result = "";

		result = (rng.nextBoolean()) ? "Heads" : "Tails";
		LOG.info(e.getAuthor() + " fliped a coin, result: " + result);
		
		ms.sendMessage(e.getChannel(), "", result);
		LOG.debug("End of: Flip.execute");
	}
}
