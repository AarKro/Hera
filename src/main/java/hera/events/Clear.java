package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BoundChannel;
import hera.misc.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Clear implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Clear.class);
	
	private static Clear instance;

	public static Clear getInstance() {
		if (instance == null)
			instance = new Clear();
		return instance;
	}

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	// constructor
	private Clear() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Clear.execute");
		gapm.getGuildAudioPlayer(e.getGuild()).scheduler.clearQueue();
		LOG.info("Queue cleared");
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Queue cleared");
		LOG.debug("End of: Clear.execute");
	}
}
