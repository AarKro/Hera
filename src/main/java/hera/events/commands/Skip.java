package hera.events.commands;

import hera.music.GuildAudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Skip extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Skip.class);

    private GuildAudioPlayerManager gapm;

    // constructor
    Skip() {
        super(null, 0, true);
        this.gapm = GuildAudioPlayerManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Skip.execute");
        gapm.getGuildAudioPlayer(e.getGuild()).scheduler.skipSong();
        LOG.info(e.getAuthor() + " skipped the currently playing song");
        LOG.debug("End of: Skip.execute");
    }
}
