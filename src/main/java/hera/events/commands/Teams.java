package hera.events.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.events.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Teams extends AbstractCommand {

	private static final Logger LOG = LoggerFactory.getLogger(Teams.class);

	private MessageSender ms;

	// constructor
	Teams() {
		super(null, 999, false);
		this.ms = MessageSender.getInstance();
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		//TODO: error messages
		LOG.debug("Start of: Teams.execute");
		int teamNumber = Integer.parseInt(params[0]);
		List<String> players = new ArrayList<>();
		for (int x = 1; x < params.length; x++)
			players.add(params[x]);

		Collections.shuffle(players);

		List<String> teams = new ArrayList<String>();
		int teamToAdd = 0;
		for (int x = 0; x < teamNumber; x++)
			teams.add("");
		for (String player : players) {
			teams.set(teamToAdd, teams.get(teamToAdd) + player + " ");
			teamToAdd++;
			if (teamToAdd >= teamNumber)
				teamToAdd = 0;
		}
		StringBuffer sb = new StringBuffer();
		int team = 1;
		for (String str : teams) {
			sb.append("Team " + team + ": " + str + "\n");
			team++;
		}
		ms.sendMessage(e.getChannel(), "Teams:", sb.toString());
		LOG.info(sb.toString());
		LOG.debug("End of: Teams.execute");
	}
}
