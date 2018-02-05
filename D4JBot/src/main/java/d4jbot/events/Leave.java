package d4jbot.events;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Leave implements Command {

	private static Leave instance;

	public static Leave getInstance() {
		if (instance == null)
			instance = new Leave();
		return instance;
	}

	// default constructor
	private Leave() {
	}

	public void execute(MessageReceivedEvent e) {
		e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel().leave();
	}
}