package javax.persistence;

import javax.persistence.spi.PersistenceUnitInfo;

import com.gwtent.orm.client.EntityManagerFactoryImpl;

/**
 * Bootstrap class that is used to obtain {@link javax.persistence.EntityManagerFactory}
 * references.
 */
public class Persistence {
  
  
  public static EntityManagerFactory createEntityManagerFactory(
      PersistenceUnitInfo info) {
    return new EntityManagerFactoryImpl(info);
  }

}
