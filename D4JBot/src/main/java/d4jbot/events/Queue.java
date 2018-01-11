package d4jbot.events;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import d4jbot.enums.BotPrefix;
import d4jbot.enums.BoundChannel;
import d4jbot.misc.GuildAudioPlayerManager;
import d4jbot.misc.MessageSender;
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
			System.out.println("1");
			BlockingQueue<AudioTrack> tracks = gapm.getGuildAudioPlayer(e.getGuild()).getScheduler().getQueue();
			System.out.println("2");

			String queue = "";
			long totalLength = 0;
			AudioTrack track;
			
			if(tracks.size() > 1) {
				System.out.println("3");
				
				track = tracks.poll();
				queue = "Now playing: " + track.getInfo().title + " | " + TimeUnit.MILLISECONDS.toHours(track.getDuration()) + " Requested by: " + e.getAuthor() + "\n\n";
				totalLength = TimeUnit.MILLISECONDS.toHours(track.getDuration());
				
				for(int i = 1; i < tracks.size(); i++) {
					track = tracks.poll();
					queue += i + ". " + track.getInfo().title + " | " + TimeUnit.MILLISECONDS.toHours(track.getDuration()) + " Requested by: " + e.getAuthor() + "\n\n";
					totalLength += TimeUnit.MILLISECONDS.toHours(track.getDuration());
				}
				System.out.println("4");
				
				track = tracks.poll();
				queue += "Total songs: " + tracks.size() + " | Total duration: " + TimeUnit.MILLISECONDS.toHours(totalLength);
				
			} else if(tracks.size() == 1) {
				System.out.println("5");
				track = tracks.poll();
				queue = "Now playing: " + track.getInfo().title + " | " + TimeUnit.MILLISECONDS.toHours(track.getDuration()) + " Requested by: " + e.getAuthor() + "\n\n";
			} else queue = "There are no songs in the queue!";
			System.out.println("6");
			
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, queue);
		}
	}
}
