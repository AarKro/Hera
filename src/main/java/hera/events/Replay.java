package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Replay implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Replay.class);
	
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
		LOG.debug("Start of: Replay.execute");
		gapm.getGuildAudioPlayer(e.getGuild()).scheduler.requeueSong();
		LOG.info(e.getAuthor() + " requeued the currently playing song");
		LOG.debug("End of: Replay.execute");
	}
}
