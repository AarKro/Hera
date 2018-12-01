package hera.events.commands;

import hera.enums.BoundChannel;
import hera.events.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Clear extends Command {

	private static final Logger LOG = LoggerFactory.getLogger(Clear.class);
	
	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	// constructor
	Clear() {
		super(null, 0, false);
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
