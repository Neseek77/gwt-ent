package com.gwtent.client.aop.advice;

import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;

/**
 * 
 * @author JamesLuo.au@gmail.com
 *
 */
public class AfterReturningAdvice extends AbstractAdvice {

	public AfterReturningAdvice(Method method, Object aspectInstance) {
		super(method, aspectInstance);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object returnValue = invocation.proceed();
		
		if (shouldInvokeByReturnValue(returnValue))
			getAdviceMethod().invoke(getAspectInstance(), getArgs(invocation, returnValue));
		
		return returnValue;
	}
	
	private boolean shouldInvokeByReturnValue(Object returnValue){
//		ClassType classType = TypeOracle.Instance.getClassType(returnValue.getClass());
//		
//		if (classType != null){
//			
//		}
		return true;
	}

}
