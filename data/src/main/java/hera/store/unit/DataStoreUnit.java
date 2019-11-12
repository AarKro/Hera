package hera.store.unit;

import hera.database.DBService;
import hera.database.entity.mapped.IMappedEntity;
import hera.database.entity.persistence.IPersistenceEntity;

import java.util.List;

public class DataStoreUnit<T extends IPersistenceEntity<M>, M extends IMappedEntity<T>> {

	DBService<T, M> DB;

	List<M> data;

	public DataStoreUnit() {
	}

	public DataStoreUnit(String entityName) {
		DB = new DBService<>(entityName);
		data = DB.readAll();
	}

	public void updateStore() {
		data = DB.readAll();
	}

	public List<M> getAll() {
		return data;
	}

	public void add(M object) {
		try {
			DB.insert(object);
			data.add(object);
		} catch(Exception e) {

		}
	}
}
