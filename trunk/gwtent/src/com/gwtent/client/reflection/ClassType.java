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


public interface ClassType extends HasAnnotations, HasMetaData {

	public Field findField(String name);

	public Method findMethod(String name, Type[] paramTypes);

	public Method findMethod(String name, String[] paramTypes);
	
	public Constructor findConstructor(String[] paramTypes);

	public Field getField(String name);

	public Field[] getFields();

	public ClassType[] getImplementedInterfaces();

	public Method getMethod(String name, Type[] paramTypes)
			throws NotFoundException;

	/*
	 * Returns the declared methods of this class (not any superclasses or
	 * superinterfaces).
	 */
	public Method[] getMethods();

	public String getName();

	public Package getPackage();

	public ClassType getSuperclass();
	
	public Object invoke(Object instance, String methodName, Object[] args) throws MethodInvokeException;

}