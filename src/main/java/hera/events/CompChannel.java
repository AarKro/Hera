package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CompChannel implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(CompChannel.class);
	
	private static CompChannel instance;

	public static CompChannel getInstance() {
		if (instance == null)
			instance = new CompChannel();
		return instance;
	}

	private MessageSender ms;

	private CompChannel() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: CompChannel.execute");
			//TODO: Check Role, Then Check Arguments, Then Create Comptryhard if following node argument is valid
				// Arguments: Node, MaxPeople, Game (Just so that name stays unique)
		LOG.debug("End of: CompChannel.execute");
	}
}
