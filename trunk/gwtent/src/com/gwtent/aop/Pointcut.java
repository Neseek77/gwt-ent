package com.gwtent.aop;

public interface Pointcut {
	ClassFilter getClassFilter();

	MethodMatcher getMethodMatcher();
}
