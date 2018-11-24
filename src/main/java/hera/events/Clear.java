package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BoundChannel;
import hera.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class Clear extends Command {

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
		super(null, 0);
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		LOG.debug("Start of: Clear.execute");
		gapm.getGuildAudioPlayer(e.getGuild()).scheduler.clearQueue();
		LOG.info("Queue cleared");
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Queue cleared");
		LOG.debug("End of: Clear.execute");
	}
}
