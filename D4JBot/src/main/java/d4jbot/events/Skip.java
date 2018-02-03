package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Skip implements Command{
	
	private MessageSender ms;
	private GuildAudioPlayerManager gapm;
	
	private static Skip instance;
	
	public static Skip getInstance() {
		if(instance == null) instance = new Skip();
		return instance;
	}
	
	// constructor
	private Skip() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}
	
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "skip")) {
			gapm.getGuildAudioPlayer(e.getGuild()).scheduler.nextTrack();
		}
	}
}
