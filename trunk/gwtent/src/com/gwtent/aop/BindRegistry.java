package com.gwtent.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.gwtent.aop.matcher.*;
import com.gwtent.client.aop.intercept.MethodInterceptor;

public class BindRegistry implements MatcherQuery{
	final List<MethodAspect> methodAspects = new ArrayList<MethodAspect>();

	public void bindInterceptor(Matcher<? super Class<?>> classMatcher,
			Matcher<? super Method> methodMatcher,
			MethodInterceptor... interceptors) {
		methodAspects.add(new MethodAspect(classMatcher, methodMatcher,
				interceptors));
	}
	
	public void bindInterceptor(Matcher<? super Class<?>> classMatcher,
			Matcher<? super Method> methodMatcher, 
			List<MethodInterceptor> interceptors){
		methodAspects.add(new MethodAspect(classMatcher, methodMatcher,
				interceptors));
	}
	
	public int size(){
		return methodAspects.size();
	}

	private static BindRegistry instance = null;

	public static BindRegistry getInstance() {
		if (instance == null)
			instance = new BindRegistry();

		return instance;
	}

	private BindRegistry() {

	}

	@Override
	public boolean matches(Class<?> clazz) {
		for (int i = 0; i < this.methodAspects.size(); i++){
			if (methodAspects.get(i).matches(clazz)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public List<MethodInterceptor> matches(Method method) {
		List<MethodInterceptor> result = new ArrayList<MethodInterceptor>();
		for (int i = 0; i < this.methodAspects.size(); i++){
			MethodAspect aspect = methodAspects.get(i);
			if (aspect.matches(method.getDeclaringClass())) {
				if (aspect.matches(method)){
					result.addAll(aspect.interceptors());
				}
			}
		}
		
		return result;
	}

}
