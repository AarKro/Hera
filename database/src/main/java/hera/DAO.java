package hera;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class DAO<T> {
	private static final String READ_ALL = "SELECT * FROM :tableName";

	private String tableName;

	public DAO(String tableName) {
		this.tableName = tableName;
	}

	public List<T> readAll() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		Query query = entityManager.createQuery(READ_ALL)
				.setParameter("tableName", tableName);

		@SuppressWarnings("unchecked")
		List<T> results = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();

		return results;
	}

	public void create(T object) {
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
