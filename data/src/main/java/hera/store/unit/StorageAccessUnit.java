package hera.store.unit;

import hera.database.DAO;
import hera.database.entities.PersistenceEntity;
import hera.store.exception.FailedAfterRetriesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class StorageAccessUnit<T extends PersistenceEntity> {
	private static final Logger LOG = LoggerFactory.getLogger(StorageAccessUnit.class);

	DAO<T> dao;

	private Class<T> cl;

	public StorageAccessUnit() {
	}

	public StorageAccessUnit(Class<T> cl, String entityName) {
		dao = new DAO<>(cl, entityName);
		this.cl = cl;

		LOG.info("StorageAccessUnit for entity {} created and initialized", cl.getName());
	}

	public List<T> getAll() {
		try {
			// TODO: implement an abstracted way of dealing with DB retries for the get method
			for(int i = 0; i < 3; i++) {
				try {
					// We want to have a log message for when we retry after the modification failed
					if (i > 0) LOG.info("Retrying previously failed DB modification ({})", i);
					return dao.getAll();
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
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to get entity of type {}", cl.getName());
			LOG.debug("Stacktrace:", e);
		}

		return null;
	}

	public T get(int id) {
		try {
			// TODO: implement an abstracted way of dealing with DB retries for the get method
			for(int i = 0; i < 3; i++) {
				try {
					// We want to have a log message for when we retry after the modification failed
					if (i > 0) LOG.info("Retrying previously failed DB modification ({})", i);
					return dao.get(id);
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
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to get entity of type {}", cl.getName());
			LOG.debug("Stacktrace:", e);
		}

		return null;
	}

	public List<T> get(Map<String, Object> whereClauses) {
		try {
			// TODO: implement an abstracted way of dealing with DB retries for the get method
			for(int i = 0; i < 3; i++) {
				try {
					// We want to have a log message for when we retry after the modification failed
					if (i > 0) LOG.info("Retrying previously failed DB modification ({})", i);
					return dao.get(whereClauses);
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
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to get entity of type {}", cl.getName());
			LOG.debug("Stacktrace:", e);
		}

		return null;
	}

	public void add(T entity) {
		try {
			retryOnFail(() -> dao.insert(entity));
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to add entity of type {}", cl.getName());
			LOG.debug("Stacktrace:", e);
		}
	}

	public void delete(T entity) {
		try {
			retryOnFail(() -> dao.delete(entity));
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to delete entity of type {}", cl.getName());
			LOG.debug("Stacktrace:", e);
		}
	}

	public void delete(int id) {
		try {
			retryOnFail(() -> dao.delete(id));
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to delete entity of type {}", cl.getName());
			LOG.debug("Stacktrace:", e);
		}
	}

	public void update(T entity) {
		try {
			retryOnFail(() -> dao.update(entity));
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to update entity of type {}", cl.getName());
			LOG.debug("Stacktrace:", e);
		}
	}

	public void upsert(T entity) {
		try {
			retryOnFail(() -> dao.upsert(entity));
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to upsert entity of type {}", cl.getName());
			LOG.debug("Stacktrace:", e);
		}
	}

	protected void retryOnFail(Runnable runnable) throws FailedAfterRetriesException {
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
