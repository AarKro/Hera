package hera.events;

import java.util.Arrays;
import java.util.List;

import hera.misc.MessageOfTheDayManager;
import hera.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Motd implements Command {

	private static Motd instance;

	public static Motd getInstance() {
		if (instance == null)
			instance = new Motd();
		return instance;
	}

	private MessageSender ms;
	private MessageOfTheDayManager motdm;

	// default constructor
	private Motd() {
		this.ms = MessageSender.getInstance();
		this.motdm = MessageOfTheDayManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			List<String> list = Arrays.asList(e.getMessage().getContent().split(" "));

			if (list.size() > 1) {
				String motd = "";
				for (int i = 1; i < list.size(); i++) {
					motd += list.get(i) + " ";
				}

				motdm.setMessageOfTheDay(e, motd);

			} else
				ms.sendMessage(e.getChannel(), "Invalid usage of $motd.\nSyntax: $motd <messageOfTheDay>");
		} else
			ms.sendMessage(e.getChannel(), "You need to be an Administrator of this server to use this command.");
	}
}
