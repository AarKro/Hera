package hera.store.unit;

import hera.database.entities.mapped.CommandMetrics;
import hera.database.entities.persistence.CommandMetricsPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CommandMetricsAccessUnit extends StorageAccessUnit<CommandMetricsPO, CommandMetrics> {
	private static final Logger LOG = LoggerFactory.getLogger(CommandMetricsAccessUnit.class);

	private static final String INCREMENT_CALL_COUNT = "UPDATE " + CommandMetricsPO.ENTITY_NAME + " c SET c.callCount = :value0 WHERE c.commandFK = :value1 AND c.guildFK = :value2 AND c.userFK = :value3 AND c.date = :value4";

	public CommandMetricsAccessUnit() {
		super(CommandMetricsPO.ENTITY_NAME);
	}

	public List<CommandMetrics> forGuild(Long guild) {
		return data.stream().filter((c) -> c.getGuild().equals(guild)).collect(Collectors.toList());
	}

	public List<CommandMetrics> forCommand(int command) {
		return data.stream().filter((c) -> c.getCommand() == command).collect(Collectors.toList());
	}

	public List<CommandMetrics> forUser(Long user) {
		return data.stream().filter((c) -> c.getUser().equals(user)).collect(Collectors.toList());
	}

	public List<CommandMetrics> forDate(LocalDate date) {
		return data.stream().filter((c) -> c.getDate().equals(date)).collect(Collectors.toList());
	}

	public List<CommandMetrics> forUserModulePerDay(Long guild, int command, Long user, LocalDate date) {
		return data.stream().filter((c) -> c.getGuild().equals(guild) && c.getCommand() == command && c.getUser().equals(user) && c.getDate().equals(date)).collect(Collectors.toList());
	}

	public void incrementOrCreate(int command, Long guild, Long user) {
		LocalDate today = LocalDate.now();

		List<CommandMetrics> matches = forUserModulePerDay(guild, command, user, today);

		// create new commands metric entry if it not already exists
		if(matches.size() < 1) {
			CommandMetrics cm = new CommandMetrics(command, guild, user, 1, today);
			add(cm);
		} else {
			CommandMetrics cm = matches.get(0);
			int newCallCount = cm.getCallCount() + 1;

			try {
				dao.query(INCREMENT_CALL_COUNT, newCallCount, command, guild, user, today);
				cm.setCallCount(newCallCount);
			} catch(Exception e) {
				LOG.error("Error while trying to increment commands metric");
				LOG.error("Command: {}, Guild: {}, User: {}, Date: {}", command, guild, user, today.toString());
				LOG.debug("Stacktrace:", e);
			}
		}
	}
}
