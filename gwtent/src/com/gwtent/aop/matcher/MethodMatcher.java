package com.gwtent.aop.matcher;

import java.lang.reflect.Method;


public interface MethodMatcher {
	public Matcher<? super Class<?>> getClassMatcher();
	public Matcher<? super Method> getMethodMatcher();
}
