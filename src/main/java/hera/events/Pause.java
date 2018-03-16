package d4jbot.events;

import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Pause implements Command {

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
		if (!gapm.getGuildAudioPlayer(e.getGuild()).player.isPaused()) {
			gapm.getGuildAudioPlayer(e.getGuild()).player.setPaused(true);
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Player paused.");
		} else {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Player is already paused.");
		}
	}
}
