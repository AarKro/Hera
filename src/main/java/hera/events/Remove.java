package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import hera.enums.BoundChannel;
import hera.misc.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Remove implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Remove.class);
	
	private static Remove instance;

	public static Remove getInstance() {
		if (instance == null)
			instance = new Remove();
		return instance;
	}

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	// constructor
	private Remove() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Remove.execute");
		String[] args = e.getMessage().getContent().split(" ");

		if (args.length == 2) {
			LOG.debug("Enough parameters to interpret command: " + args.length);
			try {
				int queuePos = Integer.parseInt(args[1]);
				if (gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getQueue().length >= queuePos && 1 <= queuePos) {

					AudioTrack trackToRemove = gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getQueue()[queuePos
							- 1];
					gapm.getGuildAudioPlayer(e.getGuild()).scheduler.removeSongFromQueue(trackToRemove);

					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Removed:", trackToRemove.getInfo().title
							+ " by " + trackToRemove.getInfo().author + " from the queue");
					LOG.info(e.getAuthor() + " removed song with songID " + queuePos);

				} else {
					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", queuePos + " is not a valid song ID.");
					LOG.debug(e.getAuthor() + " entered a invalid songID, " + queuePos);
				}
			} catch (NumberFormatException e2) {
				ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", args[1] + " is not a number.");
				LOG.error("Provided song id is not a number. songID: " + args[1]);
				LOG.error(e2.getMessage() + " : " + e2.getCause());
			}
		} else {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Invalid usage of $remove\nSyntax: $remove <songID>");
			LOG.debug(e.getAuthor() + " used command remove wrong");
		}
		
		LOG.debug("End of: Remove.execute");
	}
}
