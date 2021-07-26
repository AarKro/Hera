package hera.database;

import hera.database.entities.IPersistenceEntity;
import hera.database.entities.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO<T extends IPersistenceEntity> {
	private static final Logger LOG = LoggerFactory.getLogger(DAO.class);

	private Class<T> cl;

	public DAO(Class<T> cl) {
		this.cl = cl;
		LOG.debug("DAO for entity {} created", cl.getSimpleName());
	}

	public List<T> getAll() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		try {
			Query query = entityManager.createQuery("SELECT e FROM " + cl.getSimpleName() + " e");

			@SuppressWarnings("unchecked")
			List<T> results = query.getResultList();

			return results;
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public T get(Long id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		try {
			return entityManager.find(cl, id);
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public List<T> get(Map<String, Object> whereClauses) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		StringBuilder queryString = new StringBuilder("SELECT e FROM " + cl.getSimpleName() + " e WHERE ");


		// make a map to save the value for each value key string that the query object uses
		// deemed this necessary since map doesn't enforce keySet and valueSet to be ordered the same way, but no matter if this way it will always work
		Map<String, Object> valueParser = new HashMap<>();

		int i = 0;
		String[] wheres = new String[whereClauses.size()];
		for (String key : whereClauses.keySet()) {
			if (whereClauses.get(key) != null) {
				wheres[i] = "e." + key + " = :value" + i;
				valueParser.put("value" + i, whereClauses.get(key));
			} else {
				wheres[i] = "e." + key + " IS NULL";
			}
			i++;
		}

		queryString.append(String.join(" AND ", wheres));

		try {
			Query query = entityManager.createQuery(queryString.toString());

			valueParser.forEach(query::setParameter);

			@SuppressWarnings("unchecked")
			List<T> results = (List<T>) query.getResultList();

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
			if (!cl.getName().equals(Metric.class.getName())) LOG.info("Persisted entity of type {}", object.getClass().getSimpleName());

			return object;
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public void delete(Long id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		try {
			T entity = entityManager.find(cl, id);
			if (entity != null) {
				entityManager.remove(entity);
				LOG.info("Deleted entity of type {}", cl.getSimpleName());
			} else {
				LOG.error("No entity found for type {}", cl.getSimpleName());
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
			LOG.info("Deleted entity of type {}", entity.getClass().getSimpleName());
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
			LOG.info("Merged entity of type {}", entity.getClass().getSimpleName());
		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

	public void upsert(T entity) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		try {
			T persistedEntity = null;

			if (entity.getId() != null) persistedEntity = entityManager.find(cl, entity.getId());

			if (persistedEntity != null) {
				entityManager.merge(entity);
				LOG.info("Merged entity of type {}", entity.getClass().getSimpleName());
			} else {
				entityManager.persist(entity);
				LOG.info("Persisted entity of type {}", entity.getClass().getSimpleName());
			}

		} finally {
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}
}
