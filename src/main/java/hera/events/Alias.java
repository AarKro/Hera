package hera.events;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.constants.BotConstants;
import hera.enums.BoundChannel;
import hera.misc.MessageSender;
import hera.misc.PropertiesHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Alias implements Command {
	
	private static final Logger LOG = LoggerFactory.getLogger(Alias.class);
	
	private static Alias instance;

	public static Alias getInstance() {
		if (instance == null)
			instance = new Alias();
		return instance;
	}

	private MessageSender ms;

	// default constructor
	private Alias() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Alias.execute");
		if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			LOG.debug(e.getAuthor() + " has admin rights");
			
			String[] args = e.getMessage().getContent().split(" ");
			if (args.length == 2) {
				LOG.debug("Enough parameters to interpret command: " + args.length);
				PropertiesHandler aliasProperties = new PropertiesHandler(BotConstants.ALIAS_PROPERTY_LOCATION);
				aliasProperties.load();
				if (aliasProperties.contains(args[1])) {
					String[] aliases = aliasProperties.getProperty(args[1]).split(",");
					
					for (String alias : aliases) {
						if (alias.equals(args[2])) {
							return;
						}
					}
					aliasProperties.setProperty(args[1], aliasProperties.getProperty(args[1]) + "," + args[2]);
					
				} else {
					aliasProperties.setProperty(args[1], args[2]);
				}
				aliasProperties.save("alias created");
				LOG.info(e.getAuthor() + " has created alias " + args[2] + " for command " + args[1]);
				
			
				
			} else {
				ms.sendMessage(e.getChannel(), "Invalid usage of $alias .\nSyntax: $alias command aliases");
				LOG.debug(e.getAuthor() + " used command alias wrong");
			}
				
		} else {
			ms.sendMessage(e.getChannel(), "You need to be an Administrator of this server to use this command.");
			LOG.debug(e.getAuthor() + " is not an admin on this server");
		}
		
		LOG.debug("End of: Bind.execute");
	}
}
