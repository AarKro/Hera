package hera.events.commands;

import hera.enums.BotSettings;
import hera.enums.BoundChannel;
import hera.events.Command;
import hera.events.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Arrays;

public class Volume extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Volume.class);

    private static Volume instance;

    public static Volume getInstance() {
        if (instance == null) {
            instance = new Volume();
        }
        return instance;
    }

    private MessageSender ms;
    private GuildAudioPlayerManager gapm;

    // constructor
    public Volume() {
        super(Arrays.asList("ADMINISTRATOR"), 1, false);
        this.ms = MessageSender.getInstance();
        this.gapm = GuildAudioPlayerManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Volume.execute");
        try {
            int volume = Integer.parseInt(params[0]);
            if (volume >= 0 && volume <= 150) {
                gapm.getGuildAudioPlayer(e.getGuild()).player.setVolume(volume);
                BotSettings.BOT_VOLUME.setPropertyValue(Integer.toString(volume));
                LOG.info(e.getAuthor() + " set volume to " + volume);
            } else {
                ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Volume level must be between 0 and 150");
                LOG.debug(e.getAuthor() + " provided input that is not within the defined bounds of 0 and 150. Input: " + volume);
            }
        } catch (NumberFormatException e2) {
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Volume level must be a number");
            LOG.error(e.getAuthor() + " provided input that is not a number");
            LOG.error(e2.getMessage() + " : " + e2.getCause());
        }
        LOG.debug("End of: Volume.execute");
    }
}
