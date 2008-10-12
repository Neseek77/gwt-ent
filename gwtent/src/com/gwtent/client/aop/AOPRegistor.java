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
	
	public int size(){
		return methodAspects.size();
	}
	
	public static AOPRegistor getInstance(){
		return register;
	}
	
	private static final AOPRegistor register = new AOPRegistor();
	
	private AOPRegistor(){
		
	}
	
	
	public class MethodAspect {
		final String methodMatcherClassName;
		public String getMethodMatcherClassName() {
			return methodMatcherClassName;
		}

		public List<MethodInterceptor> getInterceptors() {
			return interceptors;
		}

		final List<MethodInterceptor> interceptors;
		
		public MethodAspect(String methodMatcherClassName, MethodInterceptor... interceptors){
			this.methodMatcherClassName = methodMatcherClassName;
			this.interceptors = Arrays.asList(interceptors);
		}
	} 
	
	public List<MethodAspect> getMethodAspects(){
		return methodAspects;
	}
	
	private List<MethodAspect> methodAspects = new ArrayList<MethodAspect>();
}
