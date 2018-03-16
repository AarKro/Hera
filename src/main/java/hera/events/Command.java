package hera.events;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public interface Command {
	
	public void execute(MessageReceivedEvent e);
}
