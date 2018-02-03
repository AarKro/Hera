package d4jbot.events;

import java.util.Arrays;
import java.util.List;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageOfTheDayManager;
import d4jbot.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Motd implements Command {

	private static Motd instance;
	
	public static Motd getInstance() {
		if (instance == null) instance = new Motd();
		return instance;
	}

	private MessageSender ms;
	private MessageOfTheDayManager motdm;
	
	// default constructor
	private Motd() {
		this.ms = MessageSender.getInstance();
		this.motdm = MessageOfTheDayManager.getInstance();
	}
		
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "motd")) {
			if(e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				List<String> list = Arrays.asList(e.getMessage().getContent().split(" "));
				
				if(list.size() > 1) {
					String motd = "";
					for(int i = 1; i < list.size(); i++) {
						motd += list.get(i) + " ";
					}
					
					motdm.setMessageOfTheDay(e, motd);
					
				} else ms.sendMessage(e.getChannel(), "Invalid usage of $motd.\nSyntax: $motd <messageOfTheDay>");
			} else ms.sendMessage(e.getChannel(), "You need to be an Administrator of this server to use this command.");
		}
	}
}
