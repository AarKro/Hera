package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import hera.enums.BoundChannel;
import hera.misc.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Np implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Np.class);
	
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
		LOG.debug("Start of: Np.execute");
		AudioTrack track = gapm.getGuildAudioPlayer(e.getGuild()).player.getPlayingTrack();
		if (track != null) {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Now playing:", track.getInfo().title + " by "
					+ track.getInfo().author + " | " + getFormattedTime(track.getDuration()));
			LOG.info(e.getAuthor() + " used command np, receiving the following information: Now playing: " + track.getInfo().title + " by "
					+ track.getInfo().author + " | " + getFormattedTime(track.getDuration()));
		}
		else {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "No song is playing right now");
			LOG.debug(e.getAuthor() + " used command np although there was no song playing");
		}
		LOG.debug("End of: Np.execute");
	}

	private String getFormattedTime(long milliseconds) {
		LOG.debug("Start of: Np.getFormattedTime");
		LOG.debug("End of: Np.getFormattedTime");
		return String.format("%02d:%02d:%02d", (milliseconds / (1000 * 60 * 60)) % 24, (milliseconds / (1000 * 60)) % 60, (milliseconds / 1000) % 60);
	}
}
