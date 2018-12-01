package hera.events.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Join extends Command {

	private static final Logger LOG = LoggerFactory.getLogger(Join.class);

	// default constructor
	Join() {
		super(null, 0, false);
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel().join();
		LOG.debug("Joind " + e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel());
	}
}
