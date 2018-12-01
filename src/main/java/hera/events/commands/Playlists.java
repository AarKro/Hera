package hera.events.commands;

import hera.constants.BotConstants;
import hera.events.Command;
import hera.events.eventSupplements.MessageSender;
import hera.propertyHandling.PropertiesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Playlists extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Playlists.class);

    private static Playlists instance;

    public static Playlists getInstance() {
        if (instance == null)
            instance = new Playlists();
        return instance;
    }

    private MessageSender ms;

    private Playlists() {
        super(null, 0, false);
        this.ms = MessageSender.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Playlists.execute");

        PropertiesHandler playlists = new PropertiesHandler(BotConstants.PLAYLISTS_PROPERTY_LOCATION);
        playlists.load();

        String output = "";
        Object[] keys = playlists.keySet().toArray();

        for(int i = 0; i < playlists.size(); i++) {
            output += String.valueOf(i + 1) + ".	" + String.valueOf(keys[i]) + "\n";
        }

        ms.sendMessage(e.getChannel(), "Playlists:", output);

        LOG.debug("End of: Playlists.execute");
    }
}
