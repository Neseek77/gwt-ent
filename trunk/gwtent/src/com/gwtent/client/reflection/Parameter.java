package com.gwtent.client.reflection;


public interface Parameter {

	public abstract Method getEnclosingMethod();

	public abstract String getName();

	public abstract String getTypeName();
	public abstract Type getType();
}