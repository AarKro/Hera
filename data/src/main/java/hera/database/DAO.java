package hera.database;

import hera.database.entity.mapped.IMappedEntity;
import hera.database.entity.persistence.IPersistenceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

	public List<M> query(String customQuery) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		Query query = entityManager.createQuery(customQuery);
		LOG.info("Executing custom query: {}", customQuery);

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
