package hera.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {
  private static final String PERSISTENCE_UNIT_NAME = "HERA";
  private static EntityManagerFactory factory;

  public static EntityManager getEntityManager() {
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

      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, configOverrides);
    }
    return factory.createEntityManager();
  }

  public static void shutdownFactory() {
    if (factory != null) {
      factory.close();
    }
  }
}