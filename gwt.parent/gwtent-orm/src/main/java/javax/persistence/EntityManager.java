/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

//
// This source code implements specifications defined by the Java
// Community Process. In order to remain compliant with the specification
// DO NOT add / change / or delete method signatures!
//
package javax.persistence;

/**
 * @version $Rev: 467742 $ $Date: 2006-10-26 05:30:38 +1000 (Thu, 26 Oct 2006) $
 */
public interface EntityManager {

	public void persist(Object entity);

	public <T> T merge(T entity);

	public void remove(Object entity);

	public <T> T find(Class<T> entityClass, Object primaryKey);

	public <T> T getReference(Class<T> entityClass, Object primaryKey);

	public void flush();

	public void setFlushMode(FlushModeType flushMode);

	public FlushModeType getFlushMode();

    public void lock(Object entity, LockModeType lockMode);

	public void refresh(Object entity);

	public void clear();

	public boolean contains(Object entity);

	public Query createQuery(String ejbqlString);

	public Query createNamedQuery(String name);

	public Query createNativeQuery(String sqlString);

	public Query createNativeQuery(String sqlString, Class resultClass);

	public Query createNativeQuery(String sqlString, String resultSetMapping);

	public void close();

	public boolean isOpen();

	public EntityTransaction getTransaction();

	public void joinTransaction();

	public Object getDelegate();
}
