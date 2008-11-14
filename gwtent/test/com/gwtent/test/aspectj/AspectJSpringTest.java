package com.gwtent.test.aspectj;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import com.gwtent.client.test.aop.Phone;

public class AspectJSpringTest extends TestCase {
	public void testMatchExplicit() throws SecurityException, NoSuchMethodException {
		String expression = "execution(* com.gwtent.client.test.aop.Phone.call(java.lang.Number))";

		Pointcut pointcut = getPointcut(expression);
		ClassFilter classFilter = pointcut.getClassFilter();
		MethodMatcher methodMatcher = pointcut.getMethodMatcher();

		assertTrue(classFilter.matches(Phone.class));
		System.out.println(classFilter.matches(this.getClass()));
		//assertFalse(classFilter.matches(this.getClass()));

		assertFalse("Should not be a runtime match", methodMatcher.isRuntime());
		Method method = Phone.class.getMethod("call", java.lang.Number.class);
		assertTrue(methodMatcher.matches(method, Phone.class));
		Method methodToString = Phone.class.getMethod("toString");
		assertFalse("Expression should match toString() method", methodMatcher.matches(methodToString, Phone.class));
	}
	
	
	private Pointcut getPointcut(String expression) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);
		return pointcut;
	}
}
