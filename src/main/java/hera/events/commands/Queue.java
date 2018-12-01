package hera.events.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import hera.enums.BoundChannel;
import hera.events.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Queue extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Queue.class);

    private MessageSender ms;
    private GuildAudioPlayerManager gapm;

    // constructor
    Queue() {
        super(null, 0, false);
        this.ms = MessageSender.getInstance();
        this.gapm = GuildAudioPlayerManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Queue.execute");
        AudioTrack[] tracks = gapm.getGuildAudioPlayer(e.getGuild()).getScheduler().getQueue();

        String queue = "";
        long totalLength = 0;

        if (tracks.length > 0) {

            AudioTrack track;
            for (int i = 0; i < tracks.length; i++) {
                track = tracks[i];
                queue += (i + 1) + ". " + track.getInfo().title + " by " + track.getInfo().author + " | "
                        + getFormattedTime(track.getDuration()) + "\n\n";
                totalLength += track.getDuration();
            }

            queue += "Total songs: " + (tracks.length) + " | Total duration: " + getFormattedTime(totalLength);

        } else {
            queue = "There are no songs in the queue!";
            LOG.debug(e.getAuthor() + " used command queue although there were no songs in the queue");
        }


        if (queue.length() >= 2000) {
            AudioTrack track;
            queue = "";
            for (int i = 0; i < tracks.length; i++) {
                track = tracks[i];
                queue += (i + 1) + ". " + track.getInfo().title + "\n\n";
            }
            queue += "Queue is displayed in compact view because of exeeding character limit.";
            LOG.info("Music Queue is displayed in compact view because of exeeding character limit");
        }

        try {
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Queue:", queue);
        } catch (Exception e2) {
            AudioTrack track;
            queue = "";
            for (int i = 0; i < 5; i++) {
                track = tracks[i];
                queue += (i + 1) + ". " + track.getInfo().title + " by " + track.getInfo().author + " | "
                        + getFormattedTime(track.getDuration()) + "\n\n";
                totalLength += track.getDuration();
            }

            queue += "Total songs: " + (tracks.length) + " | Total duration: " + getFormattedTime(totalLength);

            queue += "\n\nOnly the first 5 songs are displayed because of exeeding character limit.";

            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Queue:", queue);
            LOG.info("Compact view still exeeds discord character limit");
        } finally {
            LOG.info(e.getAuthor() + " used command Queue. Total songs: " + tracks.length + " | Total duration: " + getFormattedTime(totalLength));
        }
        LOG.debug("End of: Queue.execute");
    }

    private String getFormattedTime(long milliseconds) {
        LOG.debug("Start of: Queue.getFormattedTime");
        LOG.debug("End of: Queue.getFormattedTime");
        return String.format("%02d:%02d:%02d", (milliseconds / (1000 * 60 * 60)) % 24,
                (milliseconds / (1000 * 60)) % 60, (milliseconds / 1000) % 60);
    }
}
