package javax.persistence;

import javax.persistence.spi.PersistenceUnitInfo;

/**
 * Bootstrap class that is used to obtain {@link javax.persistence.EntityManagerFactory}
 * references.
 */
public class Persistence {
  
  
  public static EntityManagerFactory createEntityManagerFactory(
      PersistenceUnitInfo info) {
    return null;
  }

}
