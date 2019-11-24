package hera.core;

import hera.store.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Core {
	private static final Logger LOG = LoggerFactory.getLogger(Core.class);

	private static final DataStore STORE = DataStore.getInstance();

	public static void main(String[] args) {
		LOG.info("Starting Hera...");

		STORE.initialize();

		LOG.info("...Hera is now ready to use!");
	}
}
