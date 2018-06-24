package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.constants.BotConstants;
import hera.eventSupplements.MessageSender;
import hera.propertyHandling.PropertiesHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Playlists implements Command {
	
	private static final Logger LOG = LoggerFactory.getLogger(Alias.class);
	
	private static Playlists instance;

	public static Playlists getInstance() {
		if (instance == null)
			instance = new Playlists();
		return instance;
	}

	private MessageSender ms;

	private Playlists() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Playlists.execute");
		
		String[] args = e.getMessage().getContent().split(" ");
		if (args.length == 1) {
			PropertiesHandler playlists = new PropertiesHandler(BotConstants.PLAYLISTS_PROPERTY_LOCATION);
			playlists.load();
			
			String output = "";
			Object[] keys = playlists.keySet().toArray();
			
			for(int i = 0; i < playlists.size(); i++) {
				output += "					" + String.valueOf(i + 1) + ".	" + String.valueOf(keys[i]) + "\n";
			}
			
			ms.sendMessage(e.getChannel(), "Playlists:", output);
		} else {
			ms.sendMessage(e.getChannel(), "", "Invalid usage of $playlists .\nSyntax: $playlists ");
			LOG.debug(e.getAuthor() + " used command playlists wrong");
		}
		
		LOG.debug("End of: Playlists.execute");
	}
}
