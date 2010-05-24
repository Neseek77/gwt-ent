package com.gwtent.orm.client;

import java.util.List;
import java.util.Properties;

import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;

import com.gwtent.reflection.client.ClassType;

/**
 * 
 * PersistencePackage
 * Author:		JamesLuo.au@gmail.com
 * Purpose:
 * 
 * persistence.xml
 * 
 * An entity manager factory and its entity managers, together with their configuration information.
 * The set of managed classes included in the persistence unit and managed by the entity managers
 * of the entity manager factory.
 * Mapping metadata (in the form of metadata annotations and/or XML metadata) that specifies
 * the mapping of the classes to the database.
 * 
 * Entities
 * DataSource
 * Provider?
 * mapping-file?
 * 
 * transaction-type
The transaction-type attribute is used to specify whether the entity managers provided by the
entity manager factory for the persistence unit must be JTA entity managers or resource-local entity
managers. The value of this element is JTA or RESOURCE_LOCAL. If this element is not specified, the
default is JTA. A transaction-type of JTA assumes that a JTA data source will be provided—
either as specified by the jta-data-source element or provided by the container). In general, in Java EE
environments, a transaction-type of RESOURCE_LOCAL assumes that a non-JTA datasource
will be provided.
 * 
 * properties
The properties element is used to specify vendor-specific properties that apply to the persistence
unit and its entity manager factory configuration.
If a persistence provider does not recognize properties (other than those defined by this specification),
the provider must ignore those properties.
 * 
 * HowToUse:
 * 
 * 
 *
 */
public abstract class AbsPersistenceUnitInfo implements PersistenceUnitInfo{

  public abstract String getDatabaseName();

  public abstract List<ClassType> getManagedClasses();

  public List<String> getMappingFileNames() {
    return null;
  }

  public String getPersistenceProviderClassName() {
    return null;
  }

  public String getPersistenceUnitName() {
    return getClass().getName();
  }

  public Properties getProperties() {
    return null;
  }

  public PersistenceUnitTransactionType getTransactionType() {
    return PersistenceUnitTransactionType.RESOURCE_LOCAL;
  }
  
}
