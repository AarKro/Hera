package hera.metrics;

import hera.database.entities.mapped.Metric;
import hera.database.types.MetricKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static hera.store.DataStore.STORE;

public class MetricsLogger {

	private static final Logger LOG = LoggerFactory.getLogger(MetricsLogger.class);
	public static final MetricsLogger STATS = new MetricsLogger();

	private MetricsLogger() {
	}

	public void log(MetricKey key, int command, Long user, Long guild, Long value, String details) {
		STORE.metrics().add(new Metric(key, LocalDateTime.now(), command, guild, user, value, details));
	}

	public void logMessage(Long user, Long guild) {
		STORE.metrics().add(new Metric(MetricKey.MESSAGE, LocalDateTime.now(), null, guild, user, null, null));
	}

	public void logCommand(int command, Long user, Long guild) {
		STORE.metrics().add(new Metric(MetricKey.COMMAND, LocalDateTime.now(), command, guild, user, null, null));
	}

	public void logVcJoin(Long user, Long guild) {
		STORE.metrics().add(new Metric(MetricKey.VC_JOIN, LocalDateTime.now(), null, guild, user, null, null));
	}

	public void logVcLeave(Long user, Long guild) {
		STORE.metrics().add(new Metric(MetricKey.VC_LEAVE, LocalDateTime.now(), null, guild, user, null, null));
	}

	// user will just be Heras Id at this point
	public void logHeraGuildJoin(Long user, Long guild) {
		STORE.metrics().add(new Metric(MetricKey.HERA_GUILD_JOIN, LocalDateTime.now(), null, guild, user, null, null));
	}

	public void logUserGuildJoin(Long user, Long guild) {
		STORE.metrics().add(new Metric(MetricKey.USER_GUILD_JOIN, LocalDateTime.now(), null, guild, user, null, null));
	}

	public void logUserGuildLeave(Long user, Long guild) {
		STORE.metrics().add(new Metric(MetricKey.USER_GUILD_LEAVE, LocalDateTime.now(), null, guild, user, null, null));
	}
}
