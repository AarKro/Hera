package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Pause {
	
	private MessageSender ms;
	private GuildAudioPlayerManager gapm;
	
	public static Pause instance;
	
	public static Pause getInstance(MessageSender ms, GuildAudioPlayerManager gapm) {
		if(instance == null) instance = new Pause(ms, gapm);
		return instance;
	}
	
	// constructor
	private Pause(MessageSender ms, GuildAudioPlayerManager gapm) {
		this.ms = ms;
		this.gapm = gapm;
	}
	
	@EventSubscriber
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
