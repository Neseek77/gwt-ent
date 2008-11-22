package com.gwtent.client.aop.advice;

import com.gwtent.client.Virtual;
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
	
	
	
	@Virtual
	public abstract Object invoke(MethodInvocation invocation) throws Throwable;
	
	/**
	 * override this function and return the ArgsBinder what you want.
	 * @return
	 */
	@Virtual
	protected ArgsBinder getArgsBinder(){
		return ArgsBinderImpl.getInstance();
	}
	
	@Virtual
	protected Object getReturningValue(){
		return null;
	}
	
	protected Object invokeAdviceMethod(MethodInvocation invocation) throws Throwable{
		return getAdviceMethod().invoke(getAspectInstance(), getArgs(invocation, getReturningValue()));
	}
	
	public Method getAdviceMethod() {
		return method;
	}


	public Object getAspectInstance() {
		return aspectInstance;
	}
	
	public Object[] getArgs(MethodInvocation invocation){
		return getArgs(invocation, null, null);
	}
	
	public Object[] getArgs(MethodInvocation invocation, Object returnValue){
		return getArgsBinder().createArgs(invocation, getAdviceMethod(), returnValue, null);
	}
	
	public Object[] getArgs(MethodInvocation invocation, Object returnValue, Throwable throwingValue){
		return getArgsBinder().createArgs(invocation, getAdviceMethod(), returnValue, throwingValue);
	}

	
}
