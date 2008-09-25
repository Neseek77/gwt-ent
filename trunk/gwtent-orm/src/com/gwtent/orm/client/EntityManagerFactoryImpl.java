/**
 * FileName: JPAEntityManagerFactory.java
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.orm.client;


import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

public class EntityManagerFactoryImpl implements EntityManagerFactory {

	private final PersistenceUnitInfo unitInfo;
	
	public EntityManagerFactoryImpl(PersistenceUnitInfo unitInfo){
		this.unitInfo = unitInfo;
	}
	
  public void close() {
    // TODO Auto-generated method stub

  }

  public EntityManager createEntityManager() {
    return new EntityManagerImpl(unitInfo);
  }

  public EntityManager createEntityManager(Map map) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isOpen() {
    // TODO Auto-generated method stub
    return false;
  }

}
