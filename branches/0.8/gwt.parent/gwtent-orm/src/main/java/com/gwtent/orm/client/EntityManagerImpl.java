package com.gwtent.orm.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.spi.PersistenceUnitInfo;

public class EntityManagerImpl implements EntityManager {

	private final PersistenceUnitInfo unitInfo;
	
	EntityManagerImpl(PersistenceUnitInfo unitInfo){
		this.unitInfo = unitInfo;
	}
	
	
	public void clear() {
		// TODO Auto-generated method stub

	}

	
	public void close() {
		// TODO Auto-generated method stub

	}

	
	public boolean contains(Object entity) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public Query createNamedQuery(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query createNativeQuery(String sqlString) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query createNativeQuery(String sqlString, Class resultClass) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query createQuery(String ejbqlString) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void flush() {
		// TODO Auto-generated method stub

	}

	
	public Object getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public FlushModeType getFlushMode() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public EntityTransaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void joinTransaction() {
		// TODO Auto-generated method stub

	}

	
	public void lock(Object entity, LockModeType lockMode) {
		// TODO Auto-generated method stub

	}

	
	public <T> T merge(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void persist(Object entity) {
		
	}

	
	public void refresh(Object entity) {
		// TODO Auto-generated method stub

	}

	
	public void remove(Object entity) {
		// TODO Auto-generated method stub

	}

	
	public void setFlushMode(FlushModeType flushMode) {
		// TODO Auto-generated method stub

	}

}
