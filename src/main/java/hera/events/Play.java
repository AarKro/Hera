package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import hera.enums.BoundChannel;
import hera.eventSupplements.MessageSender;
import hera.instanceManagement.SingletonInstancer;
import hera.music.AudioLoadResultManager;
import hera.music.GuildAudioPlayerManager;
import hera.music.GuildMusicManager;
import hera.youtubeAPI.YoutubeAPIHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Play implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Pause.class);
	
	private static Play instance;

	public static Play getInstance() {
		if (instance == null)
			instance = new Play();
		return instance;
	}

	private MessageSender ms;
	private AudioPlayerManager apm;
	private GuildAudioPlayerManager gapm;

	// constructor
	private Play() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
		this.apm = SingletonInstancer.getAPMInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Play.execute");
		String[] args = e.getMessage().getContent().split(" ");

		String songURL = "";
		if (args.length > 1) {
			LOG.debug("Enough parameters to interpret command: " + args.length);
			if (args[1].matches("(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")) {
				songURL = args[1];
				LOG.debug("Input matches an URL. URL: " + songURL);
			} else {
				String keywords = "";
				for (int i = 1; i < args.length; i++) {
					keywords += args[i] + " ";
				}
				
				LOG.debug("Input consists of keywords to search with on YouTube");
				songURL = YoutubeAPIHandler.getInstance().getYoutubeVideoFromKeyword(keywords);
				LOG.debug("URL received after searching on YouTube: " + songURL);
			}

			GuildMusicManager musicManager = gapm.getGuildAudioPlayer(e.getGuild());
			LOG.info(e.getAuthor() + " queued song: " + songURL);
			apm.loadItemOrdered(musicManager, songURL, new AudioLoadResultManager(e, songURL, ms, musicManager));

		} else {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Invalid usage of $play | $p.\nSyntax: $play <URL>");
			LOG.debug(e.getAuthor() + " used command play wrong");
		}
		LOG.debug("End of: Play.execute");
	}
}
