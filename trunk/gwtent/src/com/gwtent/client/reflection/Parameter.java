package com.gwtent.client.reflection;

import com.gwtent.client.reflection.impl.TypeImpl;

public interface Parameter {

	public abstract Method getEnclosingMethod();

	public abstract String getName();

	public abstract Type getType();

	public abstract String toString();

	public abstract String getTypeName();

	public abstract void setTypeName(String typeName);

}