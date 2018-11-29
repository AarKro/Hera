package hera.events.commands;

import hera.events.Command;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Join extends Command {

	private static Join instance;

	public static Join getInstance() {
		if (instance == null)
			instance = new Join();
		return instance;
	}

	// default constructor
	private Join() {
		super(null, 0, false);
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel().join();
	}
}
