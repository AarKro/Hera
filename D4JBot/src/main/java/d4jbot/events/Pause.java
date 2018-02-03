package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Pause implements Command{
	
	private MessageSender ms;
	private GuildAudioPlayerManager gapm;
	
	private static Pause instance;
	
	public static Pause getInstance() {
		if(instance == null) instance = new Pause();
		return instance;
	}
	
	// constructor
	private Pause() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}
	
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "pause")) {
			if(!gapm.getGuildAudioPlayer(e.getGuild()).player.isPaused()) {
				gapm.getGuildAudioPlayer(e.getGuild()).player.setPaused(true);
				ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Player paused.");
			} else {
				ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Player is already paused.");
			}
		}
	}
}
