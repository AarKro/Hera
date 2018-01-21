package d4jbot.events;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public interface Command {

	public void onMessageReceivedEvent(MessageReceivedEvent e);
}
