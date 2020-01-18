package hera.database;

import hera.database.entities.Metric;
import hera.database.entities.PersistenceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DAO<T extends PersistenceEntity> {
	private static final Logger LOG = LoggerFactory.getLogger(DAO.class);

	private Class<T> cl;

	private String entityName;

	public DAO(Class<T> cl, String entityName) {
		this.cl = cl;
		this.entityName = entityName;
		LOG.debug("DAO for entity {} created", cl.getName());
	}

	public T get(int id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		try {
			T entity = entityManager.find(cl, id);
			return entity;
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public List<T> get(Map<String, Object> whereClauses) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		StringBuilder queryString = new StringBuilder("SELECT e FROM " + entityName + " e WHERE ");

		int i = 0;
		for (String key : whereClauses.keySet()) {
			queryString.append(key + " = :value" + i + " ");
			i++;
		}

		try {
			Query query = entityManager.createQuery(queryString.toString());

			i = 0;
			for (Object value : whereClauses.values()) {
				query.setParameter("value" + 1, value);
				i++;
			}

			@SuppressWarnings("unchecked")
			List<T> results = query.getResultList();

			return results;

		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public T insert(T object) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.persist(object);

			// only log if its not about metrics, else we would just spam our logs
			if (!cl.getName().equals(Metric.class.getName())) LOG.info("Persisted entity of type {}", object.getClass().getName());

			return object;
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public void delete(int id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		try {
			T entity = entityManager.find(cl, id);
			if (entity != null) {
				entityManager.remove(entity);
				LOG.info("Deleted entity of type {}", cl.getName());
			} else {
				LOG.error("No entity found for type {}", cl.getName());
			}
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public void delete(T entity) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.remove(entity);
			LOG.info("Deleted entity of type {}", entity.getClass().getName());
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public void update(T entity) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.merge(entity);
			LOG.info("Merged entity of type {}", entity.getClass().getName());
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public void upsert(T entity) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		try {
			T persistedEntity = entityManager.find(cl, entity.getId());
			if (persistedEntity != null) {
				entityManager.merge(entity);
				LOG.info("Merged entity of type {}", entity.getClass().getName());
			} else {
				entityManager.persist(entity);
				LOG.info("Persisted entity of type {}", entity.getClass().getName());
			}

		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}
}
