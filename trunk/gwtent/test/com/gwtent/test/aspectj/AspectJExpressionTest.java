package com.gwtent.test.aspectj;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.gwtent.client.test.aop.Phone;

public class AspectJExpressionTest extends TestCase {
	
	private Method methodCall = null;
	private Method methodToString = null;
	
	public void testMatchExplicit() throws SecurityException, NoSuchMethodException {
		String expression = "execution(* com.gwtent.client.test.aop.Phone.call(java.lang.Number))";
	
		AspectJExpress pointcut = getPointcut(expression);
		assertTrue(pointcut.matches(Phone.class));
		assertTrue(pointcut.matches(Phone.Receiver.class));
		assertTrue(pointcut.matches(this.getClass()));
		assertTrue(pointcut.matches(methodCall, Phone.class, null));
		assertFalse(pointcut.matches(methodToString, Phone.class, null));
	}
	
	public void testMatchClass(){
		String expression = "matchclass(com.gwtent.client.test.aop.TestMatcher)";
		
		AspectJExpress pointcut = getPointcut(expression);
		assertTrue(pointcut.matches(Phone.class));
		assertFalse(pointcut.matches(this.getClass()));
		
		assertTrue(pointcut.matches(methodCall, Phone.class, null));
		assertFalse(pointcut.matches(methodToString, Phone.class, null));
	}
	

	private AspectJExpress getPointcut(String expression) {
		AspectJExpress pointcut = new AspectJExpress();
		pointcut.setExpression(expression);
		return pointcut;
	}
	
	protected void setUp() throws Exception {
		methodCall = Phone.class.getMethod("call", java.lang.Number.class);
		methodToString = Phone.class.getMethod("toString");
	}
	
	protected void tearDown() throws Exception {
	}
}
