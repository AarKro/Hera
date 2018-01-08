package d4jbot.events;

import java.net.MalformedURLException;
import java.net.URL;

import d4jbot.enums.BotPrefix;
import d4jbot.misc.AudioQueueManager;
import d4jbot.misc.MessageSender;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Play {

	private MessageSender ms;
	private AudioQueueManager aqm;
	
	// default constructor
	public Play() { }
	
	// constructor
	public Play(MessageSender ms, AudioQueueManager aqm) {
		this.ms = ms;
		this.aqm = aqm;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "p") || e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "play")) {
			String[] args = e.getMessage().getContent().split(" ");

			e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel().join();
			
			URL url;
			try {
				url = new URL(args[1]);
				System.out.println(url.toString());
				aqm.play(url);
			} catch (MalformedURLException e1) {
				System.out.println("ERROR @ Play.java: MalformedURLException");
			}
		}
	}
}
