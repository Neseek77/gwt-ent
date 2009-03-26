/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


package com.gwtent.client.reflection;

import java.util.List;


public interface ClassType extends HasAnnotations, HasMetaData {

	public Field findField(String name);

	public Method findMethod(String name, Type[] paramTypes);

	//public Method findMethod(String name, String[] paramTypes);
	public Method findMethod(String name, String... paramTypes);
	
	public Constructor findConstructor(String[] paramTypes);

	/**
	 * Get field
	 * @param name the field name to find
	 * @return the field, if not found, return null
	 */
	public Field getField(String name);

	public Field[] getFields();

	public ClassType[] getImplementedInterfaces();

	/**
	 * get method
	 * @param name the method name
	 * @param paramTypes the Type of parameters, can be null.
	 * @return if not found, return null
	 */
	public Method getMethod(String name, Type[] paramTypes);
	
	public List getMetaDataMerge(String tagName);

	/*
	 * Returns the declared methods of this class (not include superclasses or
	 * superinterfaces).
	 */
	public Method[] getMethods();

	public String getName();

	//For now not support packagej
//	public Package getPackage();

	public ClassType getSuperclass();
	public Class<?> getDeclaringClass();
	
	public Object invoke(Object instance, String methodName, Object[] args) throws MethodInvokeException;

}