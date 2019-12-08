package hera.events.commands;

import hera.constants.BotConstants;
import hera.events.eventSupplements.MessageSender;
import hera.propertyHandling.PropertiesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Arrays;

public class Alias extends AbstractCommand {
	
	private static final Logger LOG = LoggerFactory.getLogger(Alias.class);
	
	private MessageSender ms;

	// default constructor
	Alias() {
		super(Arrays.asList("ADMINISTRATOR"), 2, false);
		this.ms = MessageSender.getInstance();
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		LOG.debug("Start of: Alias.execute");

		PropertiesHandler aliasProperties = new PropertiesHandler(BotConstants.ALIAS_PROPERTY_LOCATION);
		aliasProperties.load();
		if (aliasProperties.contains(params[0])) {
			String[] aliases = aliasProperties.getProperty(params[0]).split(",");

			for (String alias : aliases) {
				if (alias.equals(params[1])) {
					return;
				}
			}
			aliasProperties.setProperty(params[0], aliasProperties.getProperty(params[0]) + "," + params[1]);

		} else {
			aliasProperties.setProperty(params[0], params[1]);
		}
		aliasProperties.save("alias created");
		LOG.info(e.getAuthor() + " has created alias " + params[1] + " for commands " + params[0]);

		LOG.debug("End of: Bind.execute");
	}
}
