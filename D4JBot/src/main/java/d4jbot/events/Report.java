package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Report {
	
	private MessageSender ms;
	
	// default constructor
	public Report() { }
	
	// constructor
	public Report(MessageSender ms) {
		this.ms = ms;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "report")) {
			String[] args = e.getMessage().getContent().split(" ");

			if(args.length > 2 && args[1].startsWith("<@")) {
				String reportedUser = args[1];
				String message = "";
				for(int i = 2; i < args.length; i++) {
					message += args[i] + " ";
				}
				
				ms.sendMessage(e.getChannel(), false,   "Reporter:	 " + e.getAuthor().mention() + 
																 "\nRecipient:	" + reportedUser +
																 "\n\nReport message:\n" + message);
			} else {
				ms.sendMessage(e.getChannel(), true, "Invalid report! \nReport example: $report <@userToReport> <reportMessage>");
			}
		}
	}
}
