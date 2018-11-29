package hera.events.commands;

import hera.events.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Teams extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Teams.class);

    private static Teams instance;

    public static Teams getInstance() {
        if (instance == null) {
            instance = new Teams();
        }
        return instance;
    }

    private hera.eventSupplements.MessageSender ms;

    // constructor
    private Teams() {
        super(null, 999, false);
        this.ms = hera.eventSupplements.MessageSender.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Teams.execute");
        List<String> list = Arrays.asList(e.getMessage().getContent().split(" "));

        Collections.shuffle(list);

        String team1 = "";
        String team2 = "";
        boolean teamSwitch = true;

        for (String s : list) {
            if (teamSwitch) team1 += s + " ";
            else team2 += s + " ";
            teamSwitch = !teamSwitch;
        }

        ms.sendMessage(e.getChannel(), "Teams:", "Team 1: " + team1 + "\nTeam 2: " + team2);
        LOG.info("Team1: " + team1 + ", Team2: " + team2);
        LOG.debug("End of: Teams.execute");
    }
}
