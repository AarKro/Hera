package hera.events.commands;

import hera.enums.BoundChannel;
import hera.events.Command;
import hera.music.GuildAudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Pause extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Pause.class);

    private hera.eventSupplements.MessageSender ms;
    private GuildAudioPlayerManager gapm;

    private static Pause instance;

    public static Pause getInstance() {
        if (instance == null)
            instance = new Pause();
        return instance;
    }

    // constructor
    private Pause() {
        super(null, 0, false);
        this.ms = hera.eventSupplements.MessageSender.getInstance();
        this.gapm = GuildAudioPlayerManager.getInstance();
    }


    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Pause.execute");
        if (!gapm.getGuildAudioPlayer(e.getGuild()).player.isPaused()) {
            gapm.getGuildAudioPlayer(e.getGuild()).player.setPaused(true);
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Player paused.");
            LOG.info(e.getAuthor() + " paused the audio player");
        } else {
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Player is already paused.");
            LOG.debug(e.getAuthor() + " tried to pause the already paused audio player");
        }
        LOG.debug("End of: Pause.execute");
    }
}
