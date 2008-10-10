package com.gwtent.client.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gwtent.client.aop.intercept.MethodInterceptor;

public class AOPRegistor {

	/**
	 * 
	 * @param methodMatcherClassName The class name which implement MethodMatcher
	 * @param interceptors
	 * 
	 * @see MethodMatcher
	 */
	public void bindInterceptor(String methodMatcherClassName,
			MethodInterceptor... interceptors) {
		methodAspects.add(new MethodAspect(methodMatcherClassName, interceptors));
	}
	
	public static AOPRegistor getInstance(){
		return register;
	}
	
	private static final AOPRegistor register = new AOPRegistor();
	
	private AOPRegistor(){
		
	}
	
	
	private class MethodAspect {
		final String methodMatcherClassName;
		final List<MethodInterceptor> interceptors;
		
		public MethodAspect(String methodMatcherClassName, MethodInterceptor... interceptors){
			this.methodMatcherClassName = methodMatcherClassName;
			this.interceptors = Arrays.asList(interceptors);
		}
	} 
	
	private List<MethodAspect> methodAspects = new ArrayList<MethodAspect>();
}
