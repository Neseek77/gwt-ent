package com.gwtent.test.aspectj;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.gwtent.aop.matcher.AspectJExpress;
import com.gwtent.aop.matcher.Matchers;
import com.gwtent.client.test.aop.Phone;

public class AspectJExpressionTest extends TestCase {
	
	private Method methodCall = null;
	private Method methodCallFromString = null;
	private Method methodToString = null;
	
	public void testMatchExplicit() throws SecurityException, NoSuchMethodException {
		String expression = "execution(* com.gwtent.client.test.aop.Phone.call(java.lang.Number))";
	
		AspectJExpress pointcut = getPointcut(expression);
		assertTrue(pointcut.matches(Phone.class));
		assertTrue(pointcut.matches(Phone.Receiver.class));
		assertTrue(pointcut.matches(this.getClass()));
		assertTrue(pointcut.matches(methodCall, Phone.class));
		assertFalse(pointcut.matches(methodToString, Phone.class));
		
		assertFalse(pointcut.matches(methodCallFromString, Phone.class));
	}
	
	public void testMatchClass(){
		String expression = "matchclass(com.gwtent.test.aspectj.TestMatcher)";
		
		AspectJExpress pointcut = getPointcut(expression);
		assertTrue(pointcut.matches(Phone.class));
		assertFalse(pointcut.matches(this.getClass()));
		
		assertTrue(pointcut.matches(methodCall, Phone.class));
		assertFalse(pointcut.matches(methodToString, Phone.class));
	}
	
	public void testMatcherAndAspectJ(){
		String expression = "execution(* com.gwtent.client.test.aop.Phone.call(java.lang.Number))";
		
		assertTrue(Matchers.aspectjClass(expression).matches(Phone.class));
		assertTrue(Matchers.aspectjClass(expression).matches(Object.class));
		
		assertTrue(Matchers.aspectjMethod(expression).matches(this.methodCall));
		assertFalse(Matchers.aspectjMethod(expression).matches(this.methodCallFromString));
	}
	

	private AspectJExpress getPointcut(String expression) {
		AspectJExpress pointcut = new AspectJExpress();
		pointcut.setExpression(expression);
		return pointcut;
	}
	
	protected void setUp() throws Exception {
		methodCall = Phone.class.getMethod("call", java.lang.Number.class);
		methodToString = Phone.class.getMethod("toString");
		methodCallFromString = Phone.class.getMethod("call", java.lang.String.class);
	}
	
	protected void tearDown() throws Exception {
	}
}
