package hera.store.unit;

import hera.database.DAO;
import hera.database.entity.mapped.IMappedEntity;
import hera.database.entity.persistence.IPersistenceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StorageAccessUnit<T extends IPersistenceEntity<M>, M extends IMappedEntity<T>> {
	private static final Logger LOG = LoggerFactory.getLogger(StorageAccessUnit.class);

	DAO<T, M> dao;

	List<M> data;

	private String entityName;

	public StorageAccessUnit() {
	}

	public StorageAccessUnit(String entityName) {
		dao = new DAO<>(entityName);
		data = dao.readAll();
		this.entityName = entityName;

		LOG.info("StorageAccessUnit for entity {} created and initialized", entityName);
	}

	public void updateStore() {
		LOG.info("Updating store of entity {}", entityName);
		data = dao.readAll();
		LOG.info("Store of entity {} updated", entityName);
	}

	public List<M> getAll() {
		return data;
	}

	public void add(M object) {
		try {
			LOG.info("Persisting new entity of type {}", entityName);
			dao.insert(object);
			data.add(object);
		} catch(Exception e) {
			LOG.error("Error while trying to add entity of type {}", entityName);
			LOG.debug("Stacktrace:", e);
		}
	}
}
