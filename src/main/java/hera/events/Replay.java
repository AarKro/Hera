package d4jbot.events;

import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Replay implements Command {

	private GuildAudioPlayerManager gapm;

	private static Replay instance;

	public static Replay getInstance() {
		if (instance == null)
			instance = new Replay();
		return instance;
	}

	// constructor
	private Replay() {
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		gapm.getGuildAudioPlayer(e.getGuild()).scheduler.requeueSong();
	}
}
