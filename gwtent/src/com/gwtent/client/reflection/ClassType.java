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