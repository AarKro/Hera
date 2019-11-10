package hera.events.commands;

import hera.events.eventSupplements.MessageSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Random;

public class Flip extends AbstractCommand {

	private static final Logger LOG = LoggerFactory.getLogger(Flip.class);
	
	private MessageSender ms;

	// default constructor
	Flip() {
		super(null, 0, false);
		this.ms = MessageSender.getInstance();
	}


	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		LOG.debug("Start of: Flip.execute");
		Random rng = new Random();
		String result = "";

		result = (rng.nextBoolean()) ? "Heads" : "Tails";
		LOG.info(e.getAuthor() + " fliped a coin, result: " + result);

		ms.sendMessage(e.getChannel(), "", result);
		LOG.debug("End of: Flip.execute");
	}
}
