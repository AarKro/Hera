package hera.store.unit;

import hera.database.DAO;
import hera.database.entities.PersistenceEntity;
import hera.store.exception.FailedAfterRetriesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class StorageAccessUnit<T extends PersistenceEntity> {
	private static final Logger LOG = LoggerFactory.getLogger(StorageAccessUnit.class);

	protected DAO<T> dao;

	protected Class<T> cl;

	public StorageAccessUnit() {
	}

	public StorageAccessUnit(Class<T> cl) {
		dao = new DAO<>(cl);
		this.cl = cl;

		LOG.info("StorageAccessUnit for entity {} created and initialized", cl.getSimpleName());
	}

	public List<T> getAll() {
		try {
			Object object = retryOnFail(() -> dao.getAll());

			@SuppressWarnings("unchecked")
			List<T> result = object != null ? (List<T>) object : null;

			return result;
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to get entity of type {}", cl.getSimpleName());
			LOG.debug("Stacktrace:", e);
		}

		return null;
	}

	public T get(Long id) {
		try {
			Object object = retryOnFail(() -> dao.get(id));

			@SuppressWarnings("unchecked")
			T result = object != null ? (T) object : null;

			return result;
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to get entity of type {}", cl.getSimpleName());
			LOG.debug("Stacktrace:", e);
		}

		return null;
	}

	public List<T> get(Map<String, Object> whereClauses) {
		try {
			Object object = retryOnFail(() -> dao.get(whereClauses));

			@SuppressWarnings("unchecked")
			List<T> result = object != null ? (List<T>) object : null;

			return result;
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to get entity of type {}", cl.getSimpleName());
			LOG.debug("Stacktrace:", e);
		}

		return null;
	}

	public void add(T entity) {
		try {
			retryOnFail(() -> dao.insert(entity));
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to add entity of type {}", cl.getSimpleName());
			LOG.debug("Stacktrace:", e);
		}
	}

	public void delete(T entity) {
		try {
			retryOnFail(() -> dao.delete(entity));
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to delete entity of type {}", cl.getSimpleName());
			LOG.debug("Stacktrace:", e);
		}
	}

	public void delete(Long id) {
		try {
			retryOnFail(() -> dao.delete(id));
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to delete entity of type {}", cl.getSimpleName());
			LOG.debug("Stacktrace:", e);
		}
	}

	public void update(T entity) {
		try {
			retryOnFail(() -> dao.update(entity));
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to update entity of type {}", cl.getSimpleName());
			LOG.debug("Stacktrace:", e);
		}
	}

	public void upsert(T entity) {
		try {
			retryOnFail(() -> dao.upsert(entity));
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to upsert entity of type {}", cl.getSimpleName());
			LOG.debug("Stacktrace:", e);
		}
	}

	// Some functions return T, others return List<T>. That's why this one returns Object
	protected Object retryOnFail(Callable<Object> callable) {
		for(int i = 0; i < 3; i++) {
			try {
				// We want to have a log message for when we retry after the modification failed
				if (i > 0) LOG.info("Retrying previously failed DB modification ({})", i);
				return callable.call();
			} catch (Exception exception) {
				LOG.debug("Stacktrace:", exception);
				LOG.error("Error during DB modification, retry count: {}", i);

				try {
					Thread.sleep(500);
				} catch (InterruptedException interruptedException) {
					LOG.debug("Stacktrace:", interruptedException);
					LOG.error("Error while trying to delay the retry function call on failed DB modification");
				}
			}
		}

		throw new FailedAfterRetriesException("DB modification failed after 3 retries");
	}

	protected void retryOnFail(Runnable runnable) throws FailedAfterRetriesException {
		for(int i = 0; i < 3; i++) {
			try {
				// We want to have a log message for when we retry after the modification failed
				if (i > 0) LOG.info("Retrying previously failed DB modification ({})", i);
				runnable.run();
				return;
			} catch (Exception exception) {
				LOG.debug("Stacktrace:", exception);
				LOG.error("Error during DB modification, retry count: {}", i);

				try {
					Thread.sleep(500);
				} catch (InterruptedException interruptedException) {
					LOG.debug("Stacktrace:", interruptedException);
					LOG.error("Error while trying to delay the retry function call on failed DB modification");
				}
			}
		}

		throw new FailedAfterRetriesException("-----> DB modification failed after 3 retries <-----");
	}
}
