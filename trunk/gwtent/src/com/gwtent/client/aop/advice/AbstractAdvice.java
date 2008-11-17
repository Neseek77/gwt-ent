package com.gwtent.client.aop.advice;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;

import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;

public class AbstractAdvice implements MethodInterceptor{

	private final Method method;
	private final Object aspectInstance;
	
	public AbstractAdvice(Method method, Object aspectInstance){
		this.method = method;
		this.aspectInstance = aspectInstance;
	}
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args = ArgsGeneratorImpl.getInstance().createArgs(invocation, method);
		if (method.isAnnotationPresent(Around.class)){
			//if Around, put every thing to method, and let method choose how/when invoke next invocation
			//return method.invoke(aspectInstance, new Object[]{invocation});
			return method.invoke(aspectInstance, args);
		}else if (method.isAnnotationPresent(Before.class)){
			method.invoke(aspectInstance, args);
			return invocation.proceed();
		}else if (method.isAnnotationPresent(After.class)){
			
		}
		
		throw new RuntimeException("Advice type not supported.");
	}
	
	
	public interface ArgsBinder{
		/**
		 * Create Argument array for invoke adviced method
		 * Using a smart way. If "args" in AspectJ expression, using this
		 * If the parameter's type is unique, match the parameter 
		 *   by this type, no matter what's the parameter name
		 * if the parameter's type have more then one, check the 
		 *   parameter's name, only match if parameter name and type is the same
		 *   
		 * @param invocation MethodInvocation 
		 * @param method is the method will be invoked.
		 * @return Object[] args
		 */
		Object[] createArgs(MethodInvocation invocation, Method method);
	}
	
}
