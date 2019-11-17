package hera.store.unit;

import hera.database.entity.mapped.CommandMetrics;
import hera.database.entity.persistence.CommandMetricsPO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CommandMetricsAccessUnit extends StorageAccessUnit<CommandMetricsPO, CommandMetrics> {
	private static final String INCREMENT_CALL_COUNT = "UPDATE " + CommandMetricsPO.ENTITY_NAME + " c SET c.callCount = %s WHERE c.commandFK = %s AND c.guildFK = %s AND c.userFK = %s AND c.date = %s";

	public CommandMetricsAccessUnit(String entityName) {
		super(entityName);
	}

	public void incrementOrCreate(int command, Long guild, Long user) {
		LocalDate today = LocalDate.now();

		List<CommandMetrics> matches = data.stream()
				.filter((cm) -> cm.getCommand() == command && cm.getGuild().equals(guild) && cm.getUser().equals(user) && cm.getDate().equals(today))
				.collect(Collectors.toList());

		// create new command metric entry if it not already exists
		if(matches.size() < 1) {
			CommandMetrics cm = new CommandMetrics(command, guild, user, 1, today);
			try {
				DAO.insert(cm);
				data.add(cm);
			} catch(Exception e) {

			}
		} else {
			CommandMetrics cm = matches.get(0);
			int newCallCount = cm.getCallCount() + 1;

			String query = String.format(INCREMENT_CALL_COUNT, newCallCount, command, guild, user, today);
			try {
				DAO.query(query);
				cm.setCallCount(newCallCount);
			} catch(Exception e) {

			}
		}
	}
}
