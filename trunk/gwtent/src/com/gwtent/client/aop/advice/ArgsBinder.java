package com.gwtent.client.aop.advice;

import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;

public interface ArgsBinder {
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
