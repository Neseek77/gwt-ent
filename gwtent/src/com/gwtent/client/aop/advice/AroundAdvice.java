package com.gwtent.client.aop.advice;

import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;

public class AroundAdvice extends AbstractAdvice {

	public AroundAdvice(Method method, Object aspectInstance) {
		super(method, aspectInstance);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		return invokeAdviceMethod(invocation);
	}

}
