package com.gwtent.client.reflection;

import com.google.gwt.core.client.GWT;

public interface TypeOracle {
	public static TypeOracle Instance = (TypeOracle) GWT.create(TypeOracle.class);
	
	public ClassType getClassType(String name);
	public ClassType getClassType(Class<?> classz);
}