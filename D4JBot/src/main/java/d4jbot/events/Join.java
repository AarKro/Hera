package d4jbot.events;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Join implements Command {

	private static Join instance;

	public static Join getInstance() {
		if (instance == null)
			instance = new Join();
		return instance;
	}

	// default constructor
	private Join() {
	}

	public void execute(MessageReceivedEvent e) {
		e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel().join();
	}
}
