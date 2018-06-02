package hera.events;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.eventSupplements.MessageOfTheDayManager;
import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Motd implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Motd.class);
	
	private static Motd instance;

	public static Motd getInstance() {
		if (instance == null)
			instance = new Motd();
		return instance;
	}

	private MessageSender ms;
	private MessageOfTheDayManager motdm;

	private Motd() {
		this.ms = MessageSender.getInstance();
		this.motdm = MessageOfTheDayManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Motd.execute");
		if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			LOG.debug(e.getAuthor() + " has admin rights");
			List<String> list = Arrays.asList(e.getMessage().getContent().split(" "));

			if (list.size() > 1) {
				String motd = "";
				for (int i = 1; i < list.size(); i++) {
					motd += list.get(i) + " ";
				}

				motdm.setMessageOfTheDay(e, motd);
				LOG.info(e.getAuthor() + " set message of the day manually to: " + motd);

			} else {
				ms.sendMessage(e.getChannel(), "", "Invalid usage of $motd.\nSyntax: $motd <messageOfTheDay>");
				LOG.debug(e.getAuthor() + " used command motd wrong");
			}
		} else {
			ms.sendMessage(e.getChannel(), "", "You need to be an Administrator of this server to use this command.");
			LOG.debug(e.getAuthor() + " is not an admin of this server");
		}
		LOG.debug("End of: Motd.execute");
	}
}
