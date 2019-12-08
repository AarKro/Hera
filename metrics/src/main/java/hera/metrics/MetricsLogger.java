package hera.metrics;

import static hera.store.DataStore.STORE;

public class MetricsLogger {

	public static final MetricsLogger STATS = new MetricsLogger();

	private MetricsLogger() {
	}

	public void logCallCount(int command, Long user, Long guild) {
		STORE.commandMetrics().incrementOrCreate(command, user, guild);
	}
}
