package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BoundChannel;
import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Bind implements Command {
	
	private static final Logger LOG = LoggerFactory.getLogger(Bind.class);
	
	private static Bind instance;

	public static Bind getInstance() {
		if (instance == null)
			instance = new Bind();
		return instance;
	}

	private MessageSender ms;

	// default constructor
	private Bind() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Bind.execute");
		if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			LOG.debug(e.getAuthor() + " has admin rights");
			
			String[] args = e.getMessage().getContent().split(" ");
			if (args.length == 2) {
				LOG.debug("Enough parameters to interpret command: " + args.length);
				
				switch (args[1]) {
				case "report":
					BoundChannel.REPORT.setBoundChannel(e.getChannel());
					ms.sendMessage(e.getChannel(), "", "Report output bound to: " + e.getChannel().mention());
					LOG.info(e.getAuthor() + " has bound report output to channel " + e.getChannel().getName() + " : " + e.getChannel().getLongID());
					break;
				case "music":
					BoundChannel.MUSIC.setBoundChannel(e.getChannel());
					ms.sendMessage(e.getChannel(), "", "Music output bound to: " + e.getChannel().mention());
					LOG.info(e.getAuthor() + " has bound music output to channel " + e.getChannel().getName() + " : " + e.getChannel().getLongID());
					break;
				case "announcements":
					BoundChannel.ANNOUNCEMENTS.setBoundChannel(e.getChannel());
					ms.sendMessage(e.getChannel(), "", "Announcement messages bound to: " + e.getChannel().mention());
					LOG.info(e.getAuthor() + " has bound announcement messages to channel " + e.getChannel().getName() + " : " + e.getChannel().getLongID());
					break;
				default:
					ms.sendMessage(e.getChannel(), "", "Invalid usage of $bind.\nSyntax: $bind <report/music>");
					LOG.debug("User " + e.getAuthor() + " used command bind wrong");
				}
				
			} else {
				ms.sendMessage(e.getChannel(), "", "Invalid usage of $bind.\nSyntax: $bind <report/music>");
				LOG.debug(e.getAuthor() + " used command bind wrong");
			}
				
		} else {
			ms.sendMessage(e.getChannel(), "", "You need to be an Administrator of this server to use this command.");
			LOG.debug(e.getAuthor() + " is not an admin on this server");
		}
		
		LOG.debug("End of: Bind.execute");
	}
}
