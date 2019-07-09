package hera.events.commands;

import hera.events.eventSupplements.MessageSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Arrays;

public class DeleteMessages extends AbstractCommand {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeleteMessages.class);

	private MessageSender ms;

	// default constructor
	DeleteMessages() {
		super(Arrays.asList("ADMINISTRATOR"), 1, false);
		this.ms = MessageSender.getInstance();
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		LOG.debug("Start of: DeleteMessages.execute");
		e.getChannel().getMessageHistory(Integer.parseInt(params[0]) + 1).bulkDelete();
		LOG.debug("End of: DeleteMessages.execute");
	}
}
