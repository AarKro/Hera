package d4jbot.events;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import d4jbot.enums.BotSettings;
import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.AudioLoadResultManager;
import d4jbot.music.GuildAudioPlayerManager;
import d4jbot.music.GuildMusicManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Play {

	private MessageSender ms;
	private AudioPlayerManager apm;
	private GuildAudioPlayerManager gapm;

	// default constructor
	public Play() {	}

	// constructor
	public Play(MessageSender ms, GuildAudioPlayerManager gapm) {
		this.ms = ms;
		this.gapm = gapm;
		this.apm = this.gapm.getApm();
	}

	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if (e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "p") || e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "play")) {
				
			String[] args = e.getMessage().getContent().split(" ");

			if (args.length > 1) {
				GuildMusicManager musicManager = gapm.getGuildAudioPlayer(e.getGuild());
				apm.loadItemOrdered(musicManager, args[1], new AudioLoadResultManager(e, args[1], ms, musicManager));
			} else ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Invalid usage of $play | $p.\nSyntax: $play <URL>");
		}
	}
}
