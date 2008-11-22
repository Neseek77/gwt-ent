package com.gwtent.client.aop.advice;

import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;

/**
 * 
 * @AfterThrowing(
 *      type = "java.lang.RuntimeException",
 *      pointcut = "**"
 *      
 * @author JamesLuo.au@gmail.com
 *
 */
public class AfterThrowingAdvice extends AbstractAdvice {

	public AfterThrowingAdvice(Method method, Object aspectInstance) {
		super(method, aspectInstance);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		try{
			return invocation.proceed();
		}catch(Throwable e){
			if (shouldInvokeOnThrowing(e)){
				getAdviceMethod().invoke(getAspectInstance(), getArgs(invocation, null, e));
			}
			
			throw e;
		}
	}

	/**
	 * TODO NOT support type parameter now
	 * 
	 * @param t
	 * @return
	 */
	private boolean shouldInvokeOnThrowing(Throwable t) {
//		Class throwingType = getDiscoveredThrowingType();
//		return throwingType.isAssignableFrom(t.getClass());
		return true;
	}
	
}
