package com.gwtent.client.aop.advice;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;

import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;

public class AroundAdvice extends AbstractAdvice {

	public AroundAdvice(Method method, Object aspectInstance) {
		super(method, aspectInstance);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args = getArgs(invocation);
		if (getAdviceMethod().isAnnotationPresent(Around.class)){
			//if Around, put every thing to method, and let method choose how/when invoke next invocation
			//return method.invoke(aspectInstance, new Object[]{invocation});
			return getAdviceMethod().invoke(getAspectInstance(), args);
		}else if (getAdviceMethod().isAnnotationPresent(Before.class)){
			getAdviceMethod().invoke(getAspectInstance(), args);
			return invocation.proceed();
		}else if (getAdviceMethod().isAnnotationPresent(After.class)){
			
		}
		
		throw new RuntimeException("Advice type not supported.");
	}

}
