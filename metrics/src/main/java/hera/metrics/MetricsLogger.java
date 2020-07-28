package hera.metrics;

import hera.database.entities.Command;
import hera.database.entities.ConfigFlag;
import hera.database.entities.ConfigFlagType;
import hera.database.entities.Metric;
import hera.database.types.ConfigFlagName;
import hera.database.types.MetricKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static hera.store.DataStore.STORE;

public class MetricsLogger {

	private static final Logger LOG = LoggerFactory.getLogger(MetricsLogger.class);
	public static final MetricsLogger STATS = new MetricsLogger();

	private MetricsLogger() {
	}

	private boolean isLoggingEnabled(Long guild) {
		List<ConfigFlagType> type = STORE.configFlagTypes().forName(ConfigFlagName.METRIC_LOGGING);
		List<ConfigFlag> flags = STORE.configFlags().forGuildAndType(guild, type.get(0));
		return flags.get(0).getValue();
	}

	public void log(MetricKey key, Command command, Long user, Long guild, Long value, String details) {
		if (isLoggingEnabled(guild)) {
			STORE.metrics().add(new Metric(key, Timestamp.valueOf(LocalDateTime.now()), command, guild, user, value, details));
		}
	}

	public void logMessage(Long user, Long guild) {
		if (isLoggingEnabled(guild)) {
			STORE.metrics().add(new Metric(MetricKey.MESSAGE, Timestamp.valueOf(LocalDateTime.now()), null, guild, user, null, null));
		}
	}

	public void logCommand(Command command, Long user, Long guild) {
		if (isLoggingEnabled(guild)) {
			STORE.metrics().add(new Metric(MetricKey.COMMAND, Timestamp.valueOf(LocalDateTime.now()), command, guild, user, null, null));
		}
	}

	public void logVcJoin(Long user, Long guild) {
		if (isLoggingEnabled(guild)) {
			STORE.metrics().add(new Metric(MetricKey.VC_JOIN, Timestamp.valueOf(LocalDateTime.now()), null, guild, user, null, null));
		}
	}

	public void logVcLeave(Long user, Long guild) {
		if (isLoggingEnabled(guild)) {
			STORE.metrics().add(new Metric(MetricKey.VC_LEAVE, Timestamp.valueOf(LocalDateTime.now()), null, guild, user, null, null));
		}
	}

	// user will just be Heras Id at this point
	public void logHeraGuildJoin(Long user, Long guild) {
		if (isLoggingEnabled(guild)) {
			STORE.metrics().add(new Metric(MetricKey.HERA_GUILD_JOIN, Timestamp.valueOf(LocalDateTime.now()), null, guild, user, null, null));
		}
	}

	public void logUserGuildJoin(Long user, Long guild) {
		if (isLoggingEnabled(guild)) {
			STORE.metrics().add(new Metric(MetricKey.USER_GUILD_JOIN, Timestamp.valueOf(LocalDateTime.now()), null, guild, user, null, null));
		}
	}

	public void logUserGuildLeave(Long user, Long guild) {
		if (isLoggingEnabled(guild)) {
			STORE.metrics().add(new Metric(MetricKey.USER_GUILD_LEAVE, Timestamp.valueOf(LocalDateTime.now()), null, guild, user, null, null));
		}
	}
}
