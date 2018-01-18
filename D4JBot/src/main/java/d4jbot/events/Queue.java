package d4jbot.events;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import d4jbot.enums.BotPrefix;
import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Queue {
	
	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	// default constructor
	public Queue() {	}

	// constructor
	public Queue(MessageSender ms, GuildAudioPlayerManager gapm) {
		this.ms = ms;
		this.gapm = gapm;
	}

	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if (e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "queue")) {
			AudioTrack[] tracks = gapm.getGuildAudioPlayer(e.getGuild()).getScheduler().getQueue();

			String queue = "";
			long totalLength = 0;
			
			if(tracks.length > 0) {
				
				AudioTrack track;
				for(int i = 0; i < tracks.length; i++) {
					track = tracks[i];
					queue += (i + 1) + ". " + track.getInfo().title + " | " + getFormattedTime(track.getDuration()) + " Requested by: " + e.getAuthor() + "\n\n";
					totalLength += track.getDuration();
				}
				
				queue += "Total songs: " + (tracks.length) + " | Total duration: " + getFormattedTime(totalLength);
				
			} else queue = "There are no songs in the queue!";
			
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, queue);
		}
	}
	
	private String getFormattedTime(long milliseconds) {
		return String.format("%02d:%02d:%02d", (milliseconds / (1000 * 60 * 60)) % 24, (milliseconds / (1000 * 60)) % 60, (milliseconds / 1000) %60);
	}
}
