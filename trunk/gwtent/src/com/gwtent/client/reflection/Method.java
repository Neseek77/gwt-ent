package com.gwtent.client.reflection;

public interface Method extends AbstractMethod{

	public Object invoke(Object instance, Object[] args) throws RuntimeException;

	public Type getReturnType();
	public String getReturnTypeName();

}