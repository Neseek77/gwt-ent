package com.gwtent.client.reflection;

public abstract class Field implements HasMetaData, AccessDef, HasAnnotations {

	public abstract ClassType getEnclosingType();

	public abstract String getName();

	public abstract Type getType();

}