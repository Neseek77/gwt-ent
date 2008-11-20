package com.gwtent.client.aop.advice;

import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;

public class AfterAdvice extends AbstractAdvice {

	public AfterAdvice(Method method, Object aspectInstance) {
		super(method, aspectInstance);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try{
			return invocation.proceed();
		}finally{
			invokeAdviceMethod(invocation);
		}
	}

}
