package hera.events;

import java.util.Random;

import hera.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Flip implements Command {

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
		Random rng = new Random();
		String result = "";

		result = (rng.nextBoolean()) ? "Heads" : "Tails";

		ms.sendMessage(e.getChannel(), result);
	}
}
