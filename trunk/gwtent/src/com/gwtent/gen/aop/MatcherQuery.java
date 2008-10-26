package com.gwtent.gen.aop;

import java.lang.reflect.Method;
import java.util.List;

public interface MatcherQuery {
	/**
	 * First time query for a class, if matches, then do matches(Method)
	 * if matches, get all the MatcherClassName
	 * AOPRegistry can get all interceptors from MatcherClassName
	 * @param clazz
	 * @return
	 */
	boolean matches(Class<?> clazz);

	List<String> matches(Method method);
}