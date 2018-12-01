package hera.events.commands;

import hera.events.eventSupplements.MessageSender;
import hera.events.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Arrays;

public class DeleteMessages extends Command {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeleteMessages.class);
	
	private static DeleteMessages instance;

	public static DeleteMessages getInstance() {
		if (instance == null)
			instance = new DeleteMessages();
		return instance;
	}

	private MessageSender ms;

	// default constructor
	private DeleteMessages() {
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
