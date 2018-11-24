package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Skip implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Skip.class);
	
	private GuildAudioPlayerManager gapm;

	private static Skip instance;

	public static Skip getInstance() {
		if (instance == null)
			instance = new Skip();
		return instance;
	}

	// constructor
	private Skip() {
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Skip.execute");
		gapm.getGuildAudioPlayer(e.getGuild()).scheduler.skipSong();
		LOG.info(e.getAuthor() + " skipped the currently playing song");
		LOG.debug("End of: Skip.execute");
	}
}
