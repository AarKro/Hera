package d4jbot.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.misc.SingletonInstancer;
import d4jbot.music.AudioLoadResultManager;
import d4jbot.music.GuildAudioPlayerManager;
import d4jbot.music.GuildMusicManager;
import d4jbot.youtubeAPI.YoutubeAPIHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Play implements Command {

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
		String[] args = e.getMessage().getContent().split(" ");

		String songURL = "";
		if (args.length > 1) {
			if (args[1].matches(
					"(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")) {
				songURL = args[1];
			} else {
				String keywords = "";
				for (int i = 1; i < args.length; i++) {
					keywords += args[i] + " ";
				}

				songURL = YoutubeAPIHandler.getInstance().getYoutubeVideoFromKeyword(keywords);
			}

			GuildMusicManager musicManager = gapm.getGuildAudioPlayer(e.getGuild());
			apm.loadItemOrdered(musicManager, songURL, new AudioLoadResultManager(e, songURL, ms, musicManager));

		} else
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Invalid usage of $play | $p.\nSyntax: $play <URL>");
	}
}
