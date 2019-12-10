package hera.store.unit;

import hera.database.entities.mapped.Metric;
import hera.database.entities.persistence.MetricPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetricAccessUnit extends StorageAccessUnit<MetricPO, Metric> {

	private static final Logger LOG = LoggerFactory.getLogger(MetricAccessUnit.class);

	public MetricAccessUnit() {
		super(MetricPO.ENTITY_NAME);
	}

	@Override
	public void updateStore() {
		// overriding parent method so the command_metrics table won't load all data from the db
		// since we don't need them in Hera during run time anyway
	}

	@Override
	public void add(Metric metric) {
		try {
			dao.insert(metric);
		} catch(Exception e) {
			LOG.error("Error while trying to add entity of type MetricPO");
			LOG.debug("Stacktrace:", e);
		}
	}
}
