package com.gwtent.client.reflection;

public interface Package {

	public ClassType getType(String typeName);
	public ClassType findType(String typeName);

	public String getName();

	public ClassType[] getTypes();

	public boolean isDefault();

}