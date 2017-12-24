package d4jbot.events;

import d4jbot.misc.MessageSender;
import d4jbots.enums.BotPrefix;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Report {
	
	// default constructor
	public Report() { }
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "report")) {
			String[] args = e.getMessage().getContent().split(" ");

			if(args.length > 2 && args[1].startsWith("<@")) {
				String reportedUser = args[1];
				String message = "";
				for(int i = 2; i < args.length; i++) {
					message += args[i] + " ";
				}
				
				MessageSender.sendMessage(e.getChannel(), false,   "Reporter:	 " + e.getAuthor().mention() + 
																 "\nRecipient:	" + reportedUser +
																 "\n\nReport message:\n" + message);
			} else {
				MessageSender.sendMessage(e.getChannel(), true, "Invalid report! \nReport example: $report <@userToReport> <reportMessage>");
			}
		}
	}
}
