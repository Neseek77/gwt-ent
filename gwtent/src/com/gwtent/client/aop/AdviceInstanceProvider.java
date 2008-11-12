package com.gwtent.client.aop;

import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.reflection.Method;

public interface AdviceInstanceProvider {
	
	AdviceInstanceProvider INSTANCE = new AdviceInstanceProviderImpl(); 
	
	/**
	 * 1, Pre-Class or Pre-Object, Depends on @Aspect annotation
	 * 2, Support all possible annotations for advice
	 * Before Advice
	 * After Returning Advice
	 * After throwing Advice
	 * After Finally Advice
	 * Around Advice
	 * 
	 * @param method
	 * @return
	 */
	MethodInterceptor getInstance(Method method);
}
