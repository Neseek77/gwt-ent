package com.gwtent.client.reflection;

import com.gwtent.client.reflection.impl.MethodImpl;

public interface ClassType extends HasAnnotations, HasMetaData {

	public Field findField(String name);

	public MethodImpl findMethod(String name, Type[] paramTypes);

	public MethodImpl findMethod(String name, String[] paramTypes);

	public Field getField(String name);

	public Field[] getFields();

	public ClassType[] getImplementedInterfaces();

	public Method getMethod(String name, Type[] paramTypes)
			throws NotFoundException;

	/*
	 * Returns the declared methods of this class (not any superclasses or
	 * superinterfaces).
	 */
	public MethodImpl[] getMethods();

	public String getName();

	public Package getPackage();

	public ClassType getSuperclass();
	
	public Object invoke(Object instance, String methodName, Object[] args) throws RuntimeException;

}