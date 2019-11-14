package hera.store.unit;

import hera.database.DAO;
import hera.database.entity.mapped.IMappedEntity;
import hera.database.entity.persistence.IPersistenceEntity;

import java.util.List;

public class StorageAccessUnit<T extends IPersistenceEntity<M>, M extends IMappedEntity<T>> {

	DAO<T, M> DAO;

	List<M> data;

	public StorageAccessUnit() {
	}

	public StorageAccessUnit(String entityName) {
		DAO = new DAO<>(entityName);
		data = DAO.readAll();
	}

	public void updateStore() {
		data = DAO.readAll();
	}

	public List<M> getAll() {
		return data;
	}

	public void add(M object) {
		try {
			DAO.insert(object);
			data.add(object);
		} catch(Exception e) {

		}
	}
}
