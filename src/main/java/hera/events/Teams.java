package hera.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BotSettings;
import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Teams implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Teams.class);
	
	private static Teams instance;

	public static Teams getInstance() {
		if (instance == null) {
			instance = new Teams();
		}
		return instance;
	}

	private MessageSender ms;

	// constructor
	private Teams() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Teams.execute");
		List<String> list = Arrays.asList(e.getMessage().getContent().split(" "));

		if (list.size() > 3) {
			LOG.debug("Enough parameters to interpret command: " + list.size());
			Collections.shuffle(list);

			String team1 = "";
			String team2 = "";
			boolean teamSwitch = true;

			for (String s : list) {
				if (!s.equals(BotSettings.BOT_PREFIX.getPropertyValue() + "teams")) {
					if (teamSwitch) team1 += s + " ";
					else team2 += s + " ";
						
					teamSwitch = !teamSwitch;
				}
			}

			ms.sendMessage(e.getChannel(), "Teams:", "Team 1: " + team1 + "\nTeam 2: " + team2);
			LOG.info("Team1: " + team1 + ", Team2: " + team2);
		} else {
			ms.sendMessage(e.getChannel(), "", "$teams needs at least 3 names");
		}
		LOG.debug("End of: Teams.execute");
	}
}
