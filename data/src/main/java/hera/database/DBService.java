package hera.database;

import hera.database.entity.mapped.*;
import hera.database.entity.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

public class DBService<T extends IPersistenceEntity<M>, M extends IMappedEntity<T>> {
	private DAO<T, M> dao;

	public DBService(String entityName) {
		this.dao = new DAO<>(entityName);
	}

	public List<M> query(String query) {
		List<T> results = dao.query(query);

		if(results != null) {
			return results.stream().map(T::mapToNonePO).collect(Collectors.toList());
		}
		else {
			return null;
		}
	}

	public List<M> readAll() {
		List<T> results = dao.readAll();

		if(results != null) {
			return results.stream().map(T::mapToNonePO).collect(Collectors.toList());
		}
		else {
			return null;
		}
	}

	public void insert(M object) {
		dao.insert(object.mapToPO());
	}

	public void delete(M object) {
		dao.delete(object.mapToPO());
	}

	public void update(M object) {
		dao.update(object.mapToPO());
	}
}
