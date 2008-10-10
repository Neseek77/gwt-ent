package com.gwtent.client.test.aop;

import java.lang.reflect.Method;

import com.gwtent.aop.matcher.Matcher;
import com.gwtent.aop.matcher.Matchers;
import com.gwtent.aop.matcher.MethodMatcher;

public class TestMatcher implements MethodMatcher {

	@Override
	public Matcher<? super Class<?>> getClassMatcher() {
		return Matchers.subclassesOf(TestAOP.class);
	}

	@Override
	public Matcher<? super Method> getMethodMatcher() {
		return Matchers.any();
	}

}
