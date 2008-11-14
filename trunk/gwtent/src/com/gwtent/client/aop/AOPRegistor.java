package com.gwtent.client.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gwtent.client.aop.intercept.MethodInterceptor;

public class AOPRegistor {

	/**
	 * 
	 * @param methodMatcherClassName The class name which implement MethodMatcher
	 * @param interceptors
	 * 
	 * @see ClassMethodMatcher
	 */
	public void bindInterceptor(String methodMatcherClassName,
			MethodInterceptor... interceptors) {
		methodAspects.put(methodMatcherClassName, new MethodAspect(methodMatcherClassName, interceptors));
	}
	
	public List<MethodInterceptor> getInterceptors(String matcherClassName){
		return methodAspects.get(matcherClassName).getInterceptors();
	}
	
	public Collection<String> getMatcherClassNames(){
		return methodAspects.keySet();
	}
	
	public static AOPRegistor getInstance(){
		return register;
	}
	
	private static final AOPRegistor register = new AOPRegistor();
	
	private AOPRegistor(){
		
	}
	
//	protected List<MethodAspect> getMethodAspects(){
//		return methodAspects;
//	}

	private Map<String, MethodAspect> methodAspects = new HashMap<String, MethodAspect>();
	
	private static class MethodAspect {
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
}
