package hera.database;

import hera.database.entity.mapped.*;
import hera.database.entity.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

public class DBService<T extends IPersistenceEntity<M>, M extends IMappedEntity> {
	private DAO<T, M> dao;

	public DBService(String entityName) {
		this.dao = new DAO<>(entityName);
	}

	public List<M> readAll() {
		return dao.readAll().stream().map(T::mapToNonePO).collect(Collectors.toList());
	}

	public void create(T object) {
		dao.create(object);
	}

	public void delete(T object) {
		dao.delete(object);
	}

	public void update(T object) {
		dao.update(object);
	}
}
