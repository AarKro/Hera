package d4jbot.events;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import d4jbot.enums.BotSettings;
import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Np {

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;
	
	// default constructor
	public Np() { }

	// constructor
	public Np(MessageSender ms, GuildAudioPlayerManager gapm) {
		this.ms = ms;
		this.gapm = gapm;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "np")) {
			AudioTrack track = gapm.getGuildAudioPlayer(e.getGuild()).player.getPlayingTrack();
			if(track != null) ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Now playing:\n" + track.getInfo().title + " | " + getFormattedTime(track.getDuration()) + " Requested by: " + e.getAuthor());
			else ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "No song is playing right now");
		}
	}
	
	private String getFormattedTime(long milliseconds) {
		return String.format("%02d:%02d:%02d", (milliseconds / (1000 * 60 * 60)) % 24, (milliseconds / (1000 * 60)) % 60, (milliseconds / 1000) %60);
	}
}
