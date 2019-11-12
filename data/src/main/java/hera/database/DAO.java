package hera.database;

import hera.database.entity.mapped.IMappedEntity;
import hera.database.entity.persistence.IPersistenceEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class DAO<T extends IPersistenceEntity<M>, M extends IMappedEntity<T>> {
	private static final String READ_ALL = "SELECT * FROM :entityName";

	private String entityName;

	public DAO(String entityName) {
		this.entityName = entityName;
	}

	public List<T> query(String customQuery) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		Query query = entityManager.createQuery(customQuery);

		@SuppressWarnings("unchecked")
		List<T> results = query.getResultList();

		return results;
	}

	public List<T> readAll() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		Query query = entityManager.createQuery(READ_ALL)
				.setParameter("entityName", entityName);

		@SuppressWarnings("unchecked")
		List<T> results = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		return results;
	}

	public void insert(T object) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(object);

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void delete(T object) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.remove(object);

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void update(T object) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.merge(object);

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
