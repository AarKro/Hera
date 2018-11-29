package hera.events.commands;

import hera.enums.BoundChannel;
import hera.events.Command;
import hera.music.GuildAudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Resume extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Resume.class);

    private hera.eventSupplements.MessageSender ms;
    private GuildAudioPlayerManager gapm;

    private static Resume instance;

    public static Resume getInstance() {
        if (instance == null)
            instance = new Resume();
        return instance;
    }

    // constructor
    private Resume() {
        super(null, 0, false);
        this.ms = hera.eventSupplements.MessageSender.getInstance();
        this.gapm = GuildAudioPlayerManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Resume.execute");
        if (gapm.getGuildAudioPlayer(e.getGuild()).player.isPaused()) {
            gapm.getGuildAudioPlayer(e.getGuild()).player.setPaused(false);
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Player resumed.");
            LOG.info(e.getAuthor() + " resumed the audio player");
        } else {
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Player is not paused.");
            LOG.debug(e.getAuthor() + " used command resume although the player is not paused");
        }
        LOG.debug("End of: Resume.execute");
    }
}
