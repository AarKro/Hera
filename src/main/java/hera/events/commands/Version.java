package hera.events.commands;

import hera.enums.BotSettings;
import hera.events.Command;
import hera.events.eventSupplements.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Version extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Version.class);

    private static Version instance;

    public static Version getInstance() {
        if (instance == null) {
            instance = new Version();
        }
        return instance;
    }

    private MessageSender ms;

    private Version() {
        super(null, 0, false);
        this.ms = MessageSender.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Version.execute");
        ms.sendMessage(e.getChannel(), "Version", BotSettings.BOT_VERSION.getPropertyValue());
        LOG.info(e.getAuthor() + " requested the current version of Hera, which is " + BotSettings.BOT_VERSION.getPropertyValue());
        LOG.debug("End of: Version.execute");
    }
}
