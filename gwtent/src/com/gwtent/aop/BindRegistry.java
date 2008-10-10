package com.gwtent.aop;

import java.util.ArrayList;
import java.util.List;

import com.gwtent.aop.matcher.*;
import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.reflection.Method;

public class BindRegistry {
	final List<MethodAspect> methodAspects = new ArrayList<MethodAspect>();

	public void bindInterceptor(Matcher<? super Class<?>> classMatcher,
			Matcher<? super Method> methodMatcher,
			MethodInterceptor... interceptors) {
		methodAspects.add(new MethodAspect(classMatcher, methodMatcher,
				interceptors));
	}

	private static BindRegistry instance = null;

	public static BindRegistry getInstance() {
		if (instance == null)
			instance = new BindRegistry();

		return instance;
	}

	private BindRegistry() {

	}

}
