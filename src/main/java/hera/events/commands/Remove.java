package hera.events.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import hera.enums.BoundChannel;
import hera.events.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Remove extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Remove.class);

    private MessageSender ms;
    private GuildAudioPlayerManager gapm;

    // constructor
    Remove() {
        super(null, 1, false);
        this.ms = MessageSender.getInstance();
        this.gapm = GuildAudioPlayerManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Remove.execute");
        try {
            int queuePos = Integer.parseInt(params[0]);
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
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", params[0] + " is not a number.");
            LOG.error("Provided song id is not a number. songID: " + params[0]);
            LOG.error(e2.getMessage() + " : " + e2.getCause());
        }

        LOG.debug("End of: Remove.execute");
    }
}
