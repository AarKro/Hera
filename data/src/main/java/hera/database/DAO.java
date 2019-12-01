package hera.database;

import hera.database.entities.mapped.IMappedEntity;
import hera.database.entities.persistence.IPersistenceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DAO<T extends IPersistenceEntity<M>, M extends IMappedEntity<T>> {
	private static final Logger LOG = LoggerFactory.getLogger(DAO.class);

	private static final String READ_ALL = "SELECT t FROM %s t";

	private String entityName;

	public DAO(String entityName) {
		this.entityName = entityName;
		LOG.debug("DAO for entity {} created", entityName);
	}

	/**
	@param customQuery A JPA query. In order to process it in a generic way, variables should just be
	 the word 'value' followed by a number starting from 0 and incrementing by 1 at a time. (value0, value1, ...)
	 */
	public List<M> query(String customQuery, Object ...params) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		Query query = entityManager.createQuery(customQuery);

		for (int i = 0; i < params.length; i++) {
			if (params[i] instanceof LocalDate) query.setParameter("value" + i, Date.valueOf(((LocalDate) params[i])), TemporalType.DATE);
			else query.setParameter("value" + i, params[i]);
		}

		List<T> result;
		if (customQuery.startsWith("UPDATE")) result = executeCustomUpdateQuery(query);
		else result = executeCustomSelectQuery(query);

		entityManager.getTransaction().commit();
		entityManager.close();

		if (result != null) return result.stream().map(T::mapToNonePO).collect(Collectors.toList());
		else return null;
	}

	private List<T> executeCustomSelectQuery(Query query) {
		@SuppressWarnings("unchecked")
		List<T> results = query.getResultList();

		if(results != null) {
			LOG.info("{} results found", results.size());
			return results;
		}
		else {
			LOG.info("No results found");
			return null;
		}
	}

	private List<T> executeCustomUpdateQuery(Query query) {
		query.executeUpdate();
		return null;
	}

	public List<M> readAll() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		String stringQuery = String.format(READ_ALL, entityName);
		Query query = entityManager.createQuery(stringQuery);
		LOG.info("Read all for entity {}", entityName);

		@SuppressWarnings("unchecked")
		List<T> results = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		if(results != null) {
			LOG.info("{} results found", results.size());
			return results.stream().map(T::mapToNonePO).collect(Collectors.toList());
		}
		else {
			LOG.info("No results found");
			return null;
		}
	}

	public void insert(M object) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(object.mapToPO());
		LOG.info("Persisted entity of type {}", object.getClass().getName());

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void delete(M object) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.remove(object.mapToPO());
		LOG.info("Deleted entity of type {}", object.getClass().getName());

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void update(M object) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.merge(object.mapToPO());
		LOG.info("Merged entity of type {}", object.getClass().getName());

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
