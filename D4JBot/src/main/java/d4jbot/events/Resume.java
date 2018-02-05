package d4jbot.events;

import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Resume implements Command {

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	private static Resume instance;

	public static Resume getInstance() {
		if (instance == null)
			instance = new Resume();
		return instance;
	}

	// constructor
	private Resume() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		if (gapm.getGuildAudioPlayer(e.getGuild()).player.isPaused()) {
			gapm.getGuildAudioPlayer(e.getGuild()).player.setPaused(false);
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Player resumed.");
		} else {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Player is not paused.");
		}
	}
}
