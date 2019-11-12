package hera.store.unit;

import hera.database.entity.mapped.CommandMetrics;
import hera.database.entity.persistence.CommandMetricsPO;
import hera.store.DataStore;

import java.util.List;
import java.util.stream.Collectors;

public class CommandMetricsStoreUnit extends DataStoreUnit<CommandMetricsPO, CommandMetrics> {
	private static final DataStore STORE = DataStore.getInstance();

	private static final String INCREMENT_CALL_COUNT = "UPDATE " + CommandMetricsPO.ENTITY_NAME + " c SET c.callCount = %s WHERE c.commandFK = %s AND c.guildFK = %s AND c.userFK = %s";

	public CommandMetricsStoreUnit(String entityName) {
		super(entityName);
	}

	public void increment(int command, Long guild, Long user) {
		List<CommandMetrics> matches = STORE.commandMetrics().getAll().stream()
				.filter((cm) -> cm.getCommand() == command && cm.getGuild().equals(guild) && cm.getUser().equals(user))
				.collect(Collectors.toList());

		if(matches.size() == 1) {
			CommandMetrics cm = matches.get(0);
			int newCallCount = cm.getCallCount() + 1;

			String query = String.format(INCREMENT_CALL_COUNT, newCallCount, command, guild, user);
			try {
				DB.query(query);
				cm.setCallCount(newCallCount);
			} catch(Exception e) {

			}
		}
	}
}
