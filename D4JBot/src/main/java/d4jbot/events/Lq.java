package d4jbot.events;

import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Lq implements Command {

	private static Lq instance;

	public static Lq getInstance() {
		if (instance == null)
			instance = new Lq();
		return instance;
	}

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	// constructor
	private Lq() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		gapm.getGuildAudioPlayer(e.getGuild()).scheduler
				.setLoopQueue(!gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getLoopQueue());
		String message = (gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getLoopQueue()) ? "Loop queue enabled"
				: "Loop queue disabled";
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), message);
	}

}
