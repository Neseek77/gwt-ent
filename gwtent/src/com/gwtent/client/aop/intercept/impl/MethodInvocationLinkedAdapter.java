package com.gwtent.client.aop.intercept.impl;

import java.util.ArrayList;
import java.util.List;

import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.reflection.Method;

/**
 * Create a command chain that put all interceptors in.
 * 
 * @author JamesLuo.au@gmail.com
 *
 */
public class MethodInvocationLinkedAdapter extends MethodInvocationAdapter {

	private int invokeCount = 0;
	
	private List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
	
	public MethodInvocationLinkedAdapter(Method method,
			Object enclosingObject, List<MethodInterceptor> interceptors) {
		super(method, enclosingObject);
		this.interceptors.addAll(interceptors);
	}

	public Object proceed() throws Throwable {
		invokeCount++;
		
		return interceptors.get(invokeCount - 1).invoke(this);
	}
	
	public void reset(Object[] args){
		super.reset(args);
		invokeCount = 0;
	}
	
	public MethodInterceptor getCurrentInterceptor(){
		if (invokeCount <= 0)
			return interceptors.get(invokeCount);
		else
			return interceptors.get(invokeCount - 1);
	}

}
