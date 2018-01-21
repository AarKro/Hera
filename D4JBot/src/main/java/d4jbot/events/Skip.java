package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Skip {
	
	private MessageSender ms;
	private GuildAudioPlayerManager gapm;
	
	public static Skip instance;
	
	public static Skip getInstance(MessageSender ms, GuildAudioPlayerManager gapm) {
		if(instance == null) instance = new Skip(ms, gapm);
		return instance;
	}
	
	// constructor
	private Skip(MessageSender ms, GuildAudioPlayerManager gapm) {
		this.ms = ms;
		this.gapm = gapm;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "skip")) {
			gapm.getGuildAudioPlayer(e.getGuild()).scheduler.nextTrack();
		}
	}
}
