package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Lq {

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;
	
	// default constructor
	public Lq() { }

	// constructor
	public Lq(MessageSender ms, GuildAudioPlayerManager gapm) {
		this.ms = ms;
		this.gapm = gapm;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "lq")) {
			gapm.getGuildAudioPlayer(e.getGuild()).scheduler.setLoopQueue(!gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getLoopQueue());
			String message = (gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getLoopQueue()) ? "Loop queue enabled" : "Loop queue disabled";
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), message);
		}
	}
	
}
