package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BotSettings;
import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Prefix implements Command {

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
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Prefix.execute");
		if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			String[] args = e.getMessage().getContent().split(" ");

			if (args.length == 2) {
				LOG.debug("Enough parameters to interpret command: " + args.length);
					String prefix = args[1];
					BotSettings.BOT_PREFIX.setPropertyValue(prefix);
					LOG.info(e.getAuthor() + " set prefix to " + prefix);

			} else {
				ms.sendMessage(e.getChannel(), "", "Invalid usage!\nSyntax: $prefix <prefix>");
				LOG.debug(e.getAuthor() + " used command volume wrong");
			}
		} else {
			ms.sendMessage(e.getChannel(), "", "You need to be an Administrator of this server to use this command.");
			LOG.debug(e.getAuthor() + " used command volume but is not an admin on this server");
		}
		LOG.debug("End of: Volume.execute");
	}
}
