package com.gwtent.client.aop.advice;

import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;

public class BeforeAdvice extends AbstractAdvice {

	public BeforeAdvice(Method method, Object aspectInstance) {
		super(method, aspectInstance);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		invokeAdviceMethod(invocation);  //if exception occur, do not invoke invocation.proceed
		return invocation.proceed();
	}

}
