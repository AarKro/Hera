package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class DeleteMessages implements Command {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeleteMessages.class);
	
	private static DeleteMessages instance;

	public static DeleteMessages getInstance() {
		if (instance == null)
			instance = new DeleteMessages();
		return instance;
	}

	private MessageSender ms;

	// default constructor
	private DeleteMessages() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: DeleteMessages.execute");
		if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			LOG.debug(e.getAuthor() + " has admin rights");
			
			String[] args = e.getMessage().getContent().split(" ");
			if (args.length == 2) {
				LOG.debug("Enough parameters to interpret command: " + args.length);
				
				e.getChannel().getMessageHistory(Integer.parseInt(args[1]) + 1).bulkDelete();
				
			} else {
				ms.sendMessage(e.getChannel(), "", "Invalid usage of $deleteMessages.\nSyntax: $deleteMessages <number>");
				LOG.debug(e.getAuthor() + " used command bind wrong");
			}
				
		} else {
			ms.sendMessage(e.getChannel(), "", "You need to be an Administrator of this server to use this command.");
			LOG.debug(e.getAuthor() + " is not an admin on this server");
		}
		
		LOG.debug("End of: DeleteMessages.execute");
	}
}
