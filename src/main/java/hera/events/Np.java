package hera.events;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import hera.enums.BoundChannel;
import hera.misc.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Np implements Command {

	private static Np instance;

	public static Np getInstance() {
		if (instance == null)
			instance = new Np();
		return instance;
	}

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	// constructor
	private Np() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		AudioTrack track = gapm.getGuildAudioPlayer(e.getGuild()).player.getPlayingTrack();
		if (track != null)
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Now playing:\n" + track.getInfo().title + " by "
					+ track.getInfo().author + " | " + getFormattedTime(track.getDuration()));
		else
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "No song is playing right now");
	}

	private String getFormattedTime(long milliseconds) {
		return String.format("%02d:%02d:%02d", (milliseconds / (1000 * 60 * 60)) % 24,
				(milliseconds / (1000 * 60)) % 60, (milliseconds / 1000) % 60);
	}
}
