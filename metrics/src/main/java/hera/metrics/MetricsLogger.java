package hera.metrics;

import hera.store.DataStore;

public class MetricsLogger {

	private static final DataStore STORE = DataStore.getInstance();

	private static MetricsLogger instance;

	private MetricsLogger() {
	}

	public static MetricsLogger getInstance() {
		if (instance == null) {
			instance = new MetricsLogger();
		}

		return instance;
	}

	public void logCallCount(int command, Long user, Long guild) {
		STORE.commandMetrics().incrementOrCreate(command, user, guild);
	}
}
