package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Resume {
	
	private MessageSender ms;
	private GuildAudioPlayerManager gapm;
	
	public static Resume instance;
	
	public static Resume getInstance(MessageSender ms, GuildAudioPlayerManager gapm) {
		if(instance == null) instance = new Resume(ms, gapm);
		return instance;
	}
	
	// constructor
	private Resume(MessageSender ms, GuildAudioPlayerManager gapm) {
		this.ms = ms;
		this.gapm = gapm;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "resume")) {
			if(gapm.getGuildAudioPlayer(e.getGuild()).player.isPaused()) {
				gapm.getGuildAudioPlayer(e.getGuild()).player.setPaused(false);
				ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Player resumed.");
			} else {
				ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Player is not paused.");
			}
		}
	}
}
