package d4jbot.events;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import d4jbot.enums.BotPrefix;
import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Volume {

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;
	
	// default constructor
	public Volume() { }

	// constructor
	public Volume(MessageSender ms, GuildAudioPlayerManager gapm) {
		this.ms = ms;
		this.gapm = gapm;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "volume")) {
			String[] args = e.getMessage().getContent().split(" ");
			
			if(args.length == 2) {
				try{
					int volume = Integer.parseInt(args[1]);
					if(volume >= 0 && volume <= 150) {
						gapm.getGuildAudioPlayer(e.getGuild()).player.setVolume(volume);
					} else ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Volume level must be between 0 and 150");
				} catch(NumberFormatException e2) {
					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Volume level must be a number");
				}
			} else ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Invalid usage!\nSyntax: $volume <0 - 150>");
		}
	}
}
