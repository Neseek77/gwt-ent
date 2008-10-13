package com.gwtent.client.reflection;

import com.gwtent.client.reflection.impl.MethodImpl;

public interface ClassType {

	public abstract Field findField(String name);

	public abstract MethodImpl findMethod(String name, Type[] paramTypes);

	public abstract MethodImpl findMethod(String name, String[] paramTypes);

	public abstract Field getField(String name);

	public abstract Field[] getFields();

	public abstract ClassType[] getImplementedInterfaces();

	public abstract Method getMethod(String name, Type[] paramTypes)
			throws NotFoundException;

	/*
	 * Returns the declared methods of this class (not any superclasses or
	 * superinterfaces).
	 */
	public abstract MethodImpl[] getMethods();

	public abstract String getName();

	public abstract Package getPackage();

	public abstract ClassType getSuperclass();

}