package hera.events;

import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Skip implements Command {

//TODO implement responce message
//	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	private static Skip instance;

	public static Skip getInstance() {
		if (instance == null)
			instance = new Skip();
		return instance;
	}

	// constructor
	private Skip() {
//		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		gapm.getGuildAudioPlayer(e.getGuild()).scheduler.skipSong();
	}
}
