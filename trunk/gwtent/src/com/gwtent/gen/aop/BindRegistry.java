package com.gwtent.gen.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.gwtent.client.aop.intercept.MethodInterceptor;

public class BindRegistry{
	final List<MethodAspect> methodAspects = new ArrayList<MethodAspect>();

//	protected void bindInterceptor(Matcher<? super Class<?>> classMatcher,
//			Matcher<? super Method> methodMatcher,
//			MethodInterceptor... interceptors) {
//		methodAspects.add(new MethodAspect(classMatcher, methodMatcher,
//				interceptors));
//	}
//	
//	protected void bindInterceptor(Matcher<? super Class<?>> classMatcher,
//			Matcher<? super Method> methodMatcher, 
//			List<MethodInterceptor> interceptors){
//		methodAspects.add(new MethodAspect(classMatcher, methodMatcher,
//				interceptors));
//	}
	
	public void bindInterceptor(String matcherClassName){
		methodAspects.add(new MethodAspect(matcherClassName));
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

	public boolean matches(Class<?> clazz) {
		for (int i = 0; i < this.methodAspects.size(); i++){
			if (methodAspects.get(i).matches(clazz)) {
				return true;
			}
		}
		
		return false;
	}

	public List<String> matches(Method method) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < this.methodAspects.size(); i++){
			MethodAspect aspect = methodAspects.get(i);
			if (aspect.matches(method.getDeclaringClass())) {
				if (aspect.matches(method)){
					result.add(aspect.getMatcherClassName());
				}
			}
		}
		
		return result;
	}

}
