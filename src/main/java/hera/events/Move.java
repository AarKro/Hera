package hera.events;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import hera.enums.BoundChannel;
import hera.misc.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Move implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Move.class);
	
	private static Move instance;

	public static Move getInstance() {
		if (instance == null)
			instance = new Move();
		return instance;
	}

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	// constructor
	private Move() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Move.execute");
		String[] args = e.getMessage().getContent().split(" ");

		if (args.length == 3) {
			LOG.debug("Enough parameters to interpret command: " + args.length);
			
			try {
				int songToMove = Integer.parseInt(args[1]);
				int position = Integer.parseInt(args[2]);
				int queueLength = gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getQueue().length;

				LOG.debug("Relevant parameters for command move: songToMove: " + songToMove + ", position: " + position + ", queueLength: " + queueLength);
				
				if (songToMove <= queueLength && songToMove > 0 && position <= queueLength && position > 0) {

					AudioTrack[] queue = gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getQueue();
					AudioTrack trackToMove = queue[songToMove - 1];

					queue = ArrayUtils.insert(position - 1, queue, trackToMove.makeClone());
					queue = ArrayUtils.removeElement(queue, trackToMove);

					gapm.getGuildAudioPlayer(e.getGuild()).scheduler.setQueueAfterMove(queue);

					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Song " + trackToMove.getInfo().title + " moved");
					LOG.info(e.getAuthor() + " moved song " + trackToMove.getInfo().title);

				} else {
					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), songToMove + " or " + position + " is not a valid song ID.");
					LOG.debug(e.getAuthor() + " used a invalid song ID. Used song IDs: " + songToMove + ", " + position);
				}

			} catch (NumberFormatException e2) {
				ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "One of the provided song IDs is not a number");
				LOG.error("One of the provided song IDs is not a number");
				LOG.error(e2.getMessage() + " : " + e2.getCause());
			}
			
		} else {
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Invalid usage of $move\nSyntax: $move <songID> <songID>");
			LOG.debug(e.getAuthor() + " used command move wrong");
		}
		LOG.debug("End of: Move.execute");
	}
}
