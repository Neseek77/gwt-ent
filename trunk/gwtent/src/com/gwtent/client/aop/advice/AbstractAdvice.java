package com.gwtent.client.aop.advice;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;

import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;

public abstract class AbstractAdvice implements MethodInterceptor{

	private final Method method;
	private final Object aspectInstance;
	
	public AbstractAdvice(Method method, Object aspectInstance){
		this.method = method;
		this.aspectInstance = aspectInstance;
	}
	
	public abstract Object invoke(MethodInvocation invocation) throws Throwable;
	
	
	public Method getAdviceMethod() {
		return method;
	}


	public Object getAspectInstance() {
		return aspectInstance;
	}
	
	public Object[] getArgs(MethodInvocation invocation){
		return getArgs(invocation, null);
	}
	
	public Object[] getArgs(MethodInvocation invocation, Object returnValue){
		return ArgsGeneratorImpl.getInstance().createArgs(invocation, getAdviceMethod(), returnValue);
	}

	
}
