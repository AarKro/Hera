package d4jbot.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import d4jbot.enums.BotPrefix;
import d4jbot.misc.AudioLoadResultManager;
import d4jbot.misc.GuildAudioPlayerManager;
import d4jbot.misc.GuildMusicManager;
import d4jbot.misc.MessageSender;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Play {

	private MessageSender ms;
	private AudioPlayerManager apm;
	private GuildAudioPlayerManager gapm;

	// default constructor
	public Play() {	}

	// constructor
	public Play(MessageSender ms, AudioPlayerManager apm, GuildAudioPlayerManager gapm) {
		this.ms = ms;
		this.apm = apm;
		this.gapm = gapm;
	}

	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if (e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "p") || e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "play")) {
				
			String[] args = e.getMessage().getContent().split(" ");

			if (args.length > 1) {
				GuildMusicManager musicManager = gapm.getGuildAudioPlayer(e.getGuild());
				apm.loadItemOrdered(musicManager, args[1], new AudioLoadResultManager(e, args[1], ms, musicManager));
			} else ms.sendMessage(e.getChannel(), true, "Invalid usage of $play | $p.\nSyntax: $play <URL>");
		}
	}
}
