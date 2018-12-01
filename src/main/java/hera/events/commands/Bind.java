package hera.events.commands;

import hera.enums.BoundChannel;
import hera.events.eventSupplements.MessageSender;
import hera.events.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Arrays;

public class Bind extends Command {
	
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
		super(Arrays.asList("ADMINISTRATOR"), 1, false);
		this.ms = MessageSender.getInstance();
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		LOG.debug("Start of: Bind.execute");
		switch (params[0]) {
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
		LOG.debug("End of: Bind.execute");
	}
}
