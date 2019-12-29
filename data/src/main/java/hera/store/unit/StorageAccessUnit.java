package hera.store.unit;

import hera.database.DAO;
import hera.database.entities.mapped.IMappedEntity;
import hera.database.entities.persistence.IPersistenceEntity;
import hera.store.exception.FailedAfterRetriesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class StorageAccessUnit<T extends IPersistenceEntity<M>, M extends IMappedEntity<T>> {
	private static final Logger LOG = LoggerFactory.getLogger(StorageAccessUnit.class);

	DAO<T, M> dao;

	List<M> data;

	private String entityName;

	public StorageAccessUnit() {
	}

	public StorageAccessUnit(String entityName) {
		dao = new DAO<>(entityName);
		this.entityName = entityName;
		updateStore();

		LOG.info("StorageAccessUnit for entity {} created and initialized", entityName);
	}

	public void updateStore() {
		try {
			retryOnFail(() -> data = dao.readAll());
		} catch(Exception e) {
			LOG.error("Error while trying to update store for entity of type {}", entityName);
			LOG.debug("Stacktrace:", e);
		}
	}

	public List<M> getAll() {
		return data;
	}

	public void add(M object) {
		try {
			retryOnFail(() -> dao.insert(object));
			data.add(object);
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to add entity of type {}", entityName);
			LOG.debug("Stacktrace:", e);
		}
	}

	public void retryOnFail(Runnable runnable) throws FailedAfterRetriesException {
		for(int i = 0; i < 3; i++) {
			try {
				// We want to have a log message for when we retry after the modification failed
				if (i > 0) LOG.info("Retrying previously failed DB modification ({})", i);
				runnable.run();
				return;
			} catch (Exception e) {
				LOG.debug("Stacktrace:", e);
				LOG.error("Error during DB modification, retry count: {}", i);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					LOG.debug("Stacktrace:", e);
					LOG.error("Error while trying to delay the retry function call on failed DB modification");
				}
			}
		}

		throw new FailedAfterRetriesException("DB modification failed after 3 retries");
	}
}
