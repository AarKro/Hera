package d4jbot.events;

import org.apache.commons.lang3.ArrayUtils;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Move implements Command {

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
		String[] args = e.getMessage().getContent().split(" ");

		if (args.length == 3) {
			try {
				int songToMove = Integer.parseInt(args[1]);
				int position = Integer.parseInt(args[2]);
				int queueLength = gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getQueue().length;

				if (songToMove <= queueLength && songToMove > 0 && position <= queueLength && position > 0) {

					AudioTrack[] queue = gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getQueue();
					AudioTrack trackToMove = queue[songToMove - 1];

					queue = ArrayUtils.insert(position - 1, queue, trackToMove.makeClone());
					queue = ArrayUtils.removeElement(queue, trackToMove);

					gapm.getGuildAudioPlayer(e.getGuild()).scheduler.setQueueAfterMove(queue);

					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(),
							"Song " + trackToMove.getInfo().title + " moved");

				} else
					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(),
							songToMove + " or " + position + " is not a valid song ID.");

			} catch (NumberFormatException e2) {
				ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "One of the provided song IDs is not a number");
			}
		} else
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(),
					"Invalid usage of $move\nSyntax: $move <songID> <songID>");
	}
}
