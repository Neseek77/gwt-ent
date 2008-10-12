package com.gwtent.aop;

import java.lang.reflect.Method;
import java.util.List;

import com.gwtent.client.aop.intercept.MethodInterceptor;

public interface MatcherQuery {
	/**
	 * First time query for a class, if matches, then do matches(Method)
	 * if matches, get all the MethodInterceptor
	 * @param clazz
	 * @return
	 */
	boolean matches(Class<?> clazz);

	List<MethodInterceptor> matches(Method method);
}