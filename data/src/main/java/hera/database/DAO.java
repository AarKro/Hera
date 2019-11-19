package hera.database;

import hera.database.entity.mapped.IMappedEntity;
import hera.database.entity.persistence.IPersistenceEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class DAO<T extends IPersistenceEntity<M>, M extends IMappedEntity<T>> {
	private static final String READ_ALL = "SELECT t FROM %s t";

	private String entityName;

	public DAO(String entityName) {
		this.entityName = entityName;
	}

	public List<M> query(String customQuery) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		Query query = entityManager.createQuery(customQuery);

		@SuppressWarnings("unchecked")
		List<T> results = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		if(results != null) {
			return results.stream().map(T::mapToNonePO).collect(Collectors.toList());
		}
		else {
			return null;
		}
	}

	public List<M> readAll() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		String stringQuery = String.format(READ_ALL, entityName);
		Query query = entityManager.createQuery(stringQuery);

		@SuppressWarnings("unchecked")
		List<T> results = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		if(results != null) {
			return results.stream().map(T::mapToNonePO).collect(Collectors.toList());
		}
		else {
			return null;
		}
	}

	public void insert(M object) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(object.mapToPO());

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void delete(M object) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.remove(object.mapToPO());

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void update(M object) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.merge(object.mapToPO());

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
