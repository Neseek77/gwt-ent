package com.gwtent.client.reflection;

public interface Method extends AbstractMethod{

	public abstract Object invoke(Object instance, Object[] args);

	public abstract Type getReturnType();

}