package hera.events;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import hera.enums.BoundChannel;
import hera.misc.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Remove implements Command {

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	private static Remove instance;

	public static Remove getInstance() {
		if (instance == null)
			instance = new Remove();
		return instance;
	}

	// constructor
	private Remove() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		String[] args = e.getMessage().getContent().split(" ");

		if (args.length == 2) {
			try {
				int queuePos = Integer.parseInt(args[1]);
				if (gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getQueue().length >= queuePos && 1 <= queuePos) {

					AudioTrack trackToRemove = gapm.getGuildAudioPlayer(e.getGuild()).scheduler.getQueue()[queuePos
							- 1];
					gapm.getGuildAudioPlayer(e.getGuild()).scheduler.removeSongFromQueue(trackToRemove);

					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Removed " + trackToRemove.getInfo().title
							+ " by " + trackToRemove.getInfo().author + " from the queue");

				} else
					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), queuePos + " is not a valid song ID.");
			} catch (NumberFormatException e2) {
				ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), args[1] + " is not a number.");
			}
		} else
			ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Invalid usage of $remove\nSyntax: $remove <songID>");
	}
}
