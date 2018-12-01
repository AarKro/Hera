package hera.events.commands;

import hera.enums.BoundChannel;
import hera.events.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class LoopQueue extends Command {

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	// constructor
	LoopQueue() {
		super(null, 0, false);
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		gapm.getGuildAudioPlayer(e.getGuild()).scheduler
				.setLoopQueue(!gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getLoopQueue());
		String message = (gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getLoopQueue()) ? "Loop queue enabled"
				: "Loop queue disabled";
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", message);
	}
}
