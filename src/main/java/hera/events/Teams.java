package d4jbot.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Teams implements Command {

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
		List<String> list = Arrays.asList(e.getMessage().getContent().split(" "));

		if (list.size() > 3) {
			Collections.shuffle(list);

			String team1 = "";
			String team2 = "";
			boolean teamSwitch = true;

			for (String s : list) {
				if (!s.equals(BotSettings.BOT_PREFIX.getPropertyValue() + "teams")) {
					if (teamSwitch)
						team1 += s + " ";
					else
						team2 += s + " ";
					teamSwitch = !teamSwitch;
				}
			}

			ms.sendMessage(e.getChannel(), "Team 1: " + team1 + "\nTeam 2: " + team2);
		} else {
			ms.sendMessage(e.getChannel(), "$teams needs at least 3 following parameters / names.");
		}
	}
}
