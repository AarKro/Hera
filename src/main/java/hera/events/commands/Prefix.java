package hera.events.commands;

import hera.enums.BotSettings;
import hera.events.Command;
import hera.events.eventSupplements.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Arrays;

public class Prefix extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Prefix.class);

    private static Prefix instance;

    public static Prefix getInstance() {
        if (instance == null) {
            instance = new Prefix();
        }
        return instance;
    }

    private MessageSender ms;

    // constructor
    public Prefix() {
        super(Arrays.asList("ADMINISTRATOR"), 1, false);
        this.ms = MessageSender.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Prefix.execute");

        String prefix = params[0];
        BotSettings.BOT_PREFIX.setPropertyValue(prefix);
        LOG.info(e.getAuthor() + " set prefix to " + prefix);

        LOG.debug("End of: Volume.execute");
    }
}
