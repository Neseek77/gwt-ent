/**
 * FileName: PersistenceProviderImpl.java
 * Author:    JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */

package com.gwtent.orm.client;


import java.util.Map;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;


public class PersistenceProviderImpl
    implements PersistenceProvider {

    static final String CLASS_TRANSFORMER_OPTIONS = "ClassTransformerOptions";
    private static final String EMF_POOL = "EntityManagerFactoryPool";

    public EntityManagerFactoryImpl createEntityManagerFactory(String name,
        String resource, Map m) {
        return null;
    }

    
    public EntityManagerFactoryImpl createEntityManagerFactory(String name,
        Map m) {
        return createEntityManagerFactory(name, null, m);
    }

    public EntityManagerFactoryImpl createContainerEntityManagerFactory(
        PersistenceUnitInfo pui, Map m) {
        return null;
    }
   
}
