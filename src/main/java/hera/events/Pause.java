package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BoundChannel;
import hera.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Pause implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Pause.class);
	
	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	private static Pause instance;

	public static Pause getInstance() {
		if (instance == null)
			instance = new Pause();
		return instance;
	}

	// constructor
	private Pause() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Pause.execute");
		if (!gapm.getGuildAudioPlayer(e.getGuild()).player.isPaused()) {
			gapm.getGuildAudioPlayer(e.getGuild()).player.setPaused(true);
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Player paused.");
			LOG.info(e.getAuthor() + " paused the audio player");
		} else {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Player is already paused.");
			LOG.debug(e.getAuthor() + " tried to pause the already paused audio player");
		}
		LOG.debug("End of: Pause.execute");
	}
}
