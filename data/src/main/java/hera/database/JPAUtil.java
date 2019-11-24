package hera.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {
  private static final Logger LOG = LoggerFactory.getLogger(JPAUtil.class);

  private static final String PERSISTENCE_UNIT_NAME = "HERA";

  private static EntityManagerFactory factory;

  static EntityManager getEntityManager() {
    if (factory == null) {
      Map<String, String> env = System.getenv();
      Map<String, Object> configOverrides = new HashMap<>();
      for (String envName : env.keySet()) {
        switch(envName) {
          case "HERA_JDBC_USER":
            configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
            break;
          case "HERA_JDBC_PWD":
            configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
            break;
          case "HERA_JDBC_URL":
            configOverrides.put("javax.persistence.jdbc.url", env.get(envName));
            break;
        }
      }

      LOG.info("Creating DB connection");
      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, configOverrides);
      LOG.info("EntityManagerFactory {} created", PERSISTENCE_UNIT_NAME);
      LOG.info("Successfully connected to DB");
    }
    return factory.createEntityManager();
  }

  static void shutdownFactory() {
    if (factory != null) {
      factory.close();
      LOG.info("EntityManagerFactory {} closed", PERSISTENCE_UNIT_NAME);
    }
  }
}