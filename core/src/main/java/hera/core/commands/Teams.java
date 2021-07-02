package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Teams {
    public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
        int teamCount = 2;
        int start = 1;

        try {
            teamCount = Integer.parseInt(params.get(0));
        } catch (NumberFormatException n) {
            // no team amount specified
            start = 0;
        }

        List<String> names = new ArrayList<>();
        for (int x = start; x < params.size(); x++) {
            names.add(params.get(x));
        }

        Collections.shuffle(names);

        int size = params.size() - start;
        int remains = size % teamCount;
        int teamSize = size / teamCount;

        List<List<String>> teams = new ArrayList<>();

        int counter = 0;
        for (int x = 0; x < teamCount; x++) {
            List<String> team = new ArrayList<>();
            for (int y = 0; y < teamSize; y++) {
                team.add(names.get(counter++));
            }
            if (remains > 0) {
                team.add(names.get(counter++));
                remains--;
            }
            teams.add(team);
        }

        return MessageHandler.send(channel, MessageSpec.getDefaultSpec(messageSpec -> messageSpec.setDescription(makeMessage(teams, guild)))).then();
    }

    private static String makeMessage(List<List<String>> teams, Guild guild) {
        StringBuilder sb = new StringBuilder();
        Localisation teamName = HeraUtil.getLocalisation(LocalisationKey.COMMAND_TEAM_TEAM, guild);
        for (int x = 0;x < teams.size();x++) {
            sb.append(String.format("%s %d: ", teamName.getValue(), x + 1));
            sb.append(String.join(", ", teams.get(x)));
            sb.append("\n");
        }

        return sb.toString();
    }
}
