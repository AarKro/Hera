package hera.events.commands;

import hera.events.Command;
import hera.events.eventSupplements.MessageOfTheDayManager;
import hera.events.eventSupplements.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Arrays;

public class MessageOfTheDay extends Command {

	private static final Logger LOG = LoggerFactory.getLogger(MessageOfTheDay.class);
	
	private static MessageOfTheDay instance;

	public static MessageOfTheDay getInstance() {
		if (instance == null)
			instance = new MessageOfTheDay();
		return instance;
	}

	private MessageSender ms;
	private MessageOfTheDayManager motdm;

	private MessageOfTheDay() {
		super(Arrays.asList("ADMINISTRATOR"), 1, true);
		this.ms = MessageSender.getInstance();
		this.motdm = MessageOfTheDayManager.getInstance();
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		LOG.debug("Start of: MessageOfTheDay.execute");

		motdm.setMessageOfTheDay(e, params[0]);
		LOG.info(e.getAuthor() + " set message of the day manually to: " + params[0]);

		LOG.debug("End of: MessageOfTheDay.execute");
	}
}
