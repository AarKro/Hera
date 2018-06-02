package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BoundChannel;
import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Report implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Report.class);
	
	private static Report instance;

	public static Report getInstance() {
		if (instance == null) {
			instance = new Report();
		}
		return instance;
	}

	private MessageSender ms;

	// constructor
	private Report() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Report.execute");
		String[] args = e.getMessage().getContent().split(" ");

		if (args.length > 2 && args[1].startsWith("<@")) {
			LOG.debug("Enough parameters to interpret command: " + args.length);
			
			String reportedUser = args[1];
			String message = "";
			for (int i = 2; i < args.length; i++) {
				message += args[i] + " ";
			}

			ms.sendMessage(BoundChannel.REPORT.getBoundChannel(), "", "Reporter:	 " + e.getAuthor().mention()
					+ "\nRecipient:	" + reportedUser + "\n\nReport message:\n" + message);
			ms.sendMessage(e.getChannel(), "", "Reported " + reportedUser);
			LOG.info(e.getAuthor() + " reported user " + reportedUser + " with message: " + message);
		} else {
			ms.sendMessage(e.getChannel(), "", "Invalid report! \nReport example: $report <@userToReport> <reportMessage>");
			LOG.debug(e.getAuthor() + " used command report wrong");
		}
		LOG.debug("End of: Report.execute");
	}
}
