package com.gwtent.client.aop.intercept.impl;

import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;

public abstract class MethodInvocationAdapter implements MethodInvocation {

	private final Method method;
	private Object[] args;
	private final Object enclosingObject;
		
	public MethodInvocationAdapter(Method method, Object enclosingObject){
		this.method = method;
		this.args = null;
		this.enclosingObject = enclosingObject;
	}
	
	public Method getMethod() {
		return method;
	}

	public Object[] getArguments() {
		return args;
	}

	public Object getThis() {
		return enclosingObject;
	}

	/**
	 * Reset MethodInvocation, before first invoke "proceed", 
	 * Caller must call this function first. 
	 * @param args
	 */
	public void reset(Object[] args){
		this.args = args;
	}
	
	public abstract Object proceed() throws Throwable;

}
