package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Bind implements Command {
	private static Bind instance;
	
	public static Bind getInstance() {
		if (instance == null) instance = new Bind();
		return instance;
	}

	private MessageSender ms;
	
	// default constructor
	private Bind() { 
		this.ms = MessageSender.getInstance();
	}
	
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "bind")) {
			if(e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			
				String[] args = e.getMessage().getContent().split(" ");
				if(args.length == 2) {
					switch(args[1]) {
					case "report":
						BoundChannel.REPORT.setBoundChannel(e.getChannel());
						ms.sendMessage(e.getChannel(), "Report output bound to: " + e.getChannel().mention());
						break;
					case "music":
						BoundChannel.MUSIC.setBoundChannel(e.getChannel());
						ms.sendMessage(e.getChannel(), "Music output bound to: " + e.getChannel().mention());
						break;
					case "announcements":
						BoundChannel.ANNOUNCEMENTS.setBoundChannel(e.getChannel());
						ms.sendMessage(e.getChannel(), "Announcement messages bound to: " + e.getChannel().mention());
						break;
					default: 
						ms.sendMessage(e.getChannel(), "Invalid usage of $bind.\nSyntax: $bind <report/music>");
					}
				} else ms.sendMessage(e.getChannel(), "Invalid usage of $bind.\nSyntax: $bind <report/music>");

			} else ms.sendMessage(e.getChannel(), "You need to be an Administrator of this server to use this command.");
		}
	}
}
