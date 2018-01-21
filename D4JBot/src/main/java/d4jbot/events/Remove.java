package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Remove {
	
	private MessageSender ms;
	private GuildAudioPlayerManager gapm;
	
	public static Remove instance;
	
	public static Remove getInstance(MessageSender ms, GuildAudioPlayerManager gapm) {
		if(instance == null) instance = new Remove(ms, gapm);
		return instance;
	}
	
	// constructor
	private Remove(MessageSender ms, GuildAudioPlayerManager gapm) {
		this.ms = ms;
		this.gapm = gapm;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "remove") || e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "rm")) {
			String[] args = e.getMessage().getContent().split(" ");
			
			if(args.length == 2) {
				try {
					int queuePos = Integer.parseInt(args[1]);
					if(gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getQueue().length >= queuePos && 1 <= queuePos) {
						
						gapm.getGuildAudioPlayer(e.getGuild()).scheduler.removeSongFromQueue(gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getQueue()[queuePos - 1]);
						
					} else ms.sendMessage(e.getChannel(), queuePos + " is not a valid song ID.");
				} catch(NumberFormatException e2) {
					ms.sendMessage(e.getChannel(), args[0] + " is not a number.");
				}
			} else ms.sendMessage(e.getChannel(), "Invalid usage of $remove\nSyntax: $remove <songID>");
		}
	}
}
