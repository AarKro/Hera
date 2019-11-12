package hera.store;

import hera.database.DBService;
import hera.database.entity.mapped.IMappedEntity;
import hera.database.entity.persistence.IPersistenceEntity;

import java.util.List;

public class DataStoreUnit<T extends IPersistenceEntity<M>, M extends IMappedEntity> {

	private DBService<T, M> DB;

	private List<M> data;

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
}
