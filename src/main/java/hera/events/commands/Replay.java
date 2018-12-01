package hera.events.commands;

import hera.music.GuildAudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Replay extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Replay.class);

    private GuildAudioPlayerManager gapm;

    // constructor
    Replay() {
        super(null, 0, false);
        this.gapm = GuildAudioPlayerManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Replay.execute");
        gapm.getGuildAudioPlayer(e.getGuild()).scheduler.requeueSong();
        LOG.info(e.getAuthor() + " requeued the currently playing song");
        LOG.debug("End of: Replay.execute");
    }
}
