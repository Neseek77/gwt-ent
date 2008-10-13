package com.gwtent.client.reflection;

public interface Type {

	public String getQualifiedSourceName();
	public String getParameterizedQualifiedSourceName();

	public String getSimpleSourceName();

	public ClassType isClass();

	public ClassType isClassOrInterface();

	public ClassType isInterface();
	
	public ArrayType isArray();

	public PrimitiveType isPrimitive();

}