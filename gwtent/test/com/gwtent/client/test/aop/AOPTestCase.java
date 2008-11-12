package com.gwtent.client.test.aop;


import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.gwtent.client.aop.AOPRegistor;
import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.aop.intercept.MethodInvocation;

import com.gwtent.client.test.aop.Phone;
import com.gwtent.client.test.aop.Phone.Receiver;

public class AOPTestCase extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.gwtent.GwtEntTest";
	}

	protected void gwtSetUp() throws Exception {
		AOPRegistor.getInstance().bindInterceptor(
				"com.gwtent.client.test.aop.TestMatcher", new PhoneLoggerInterceptor(),
				new PhoneRedirectInterceptor());
	}

	@Aspect
	public static class PhoneLoggerInterceptor implements MethodInterceptor {
		
		//execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)
		@Around("com.gwtent.client.test.aop.AOPTestCase.Phone.*")
		public Object invoke(MethodInvocation invocation) throws Throwable {
			for (Object arg : invocation.getArguments()){
				System.out.println("Do something in PhoneLoggerInterceptor...");
				
				if (arg instanceof Number)
					System.out.println("CALL: " + arg);
			}

			return invocation.proceed();
		}
	}

	@Aspect
	public static class PhoneRedirectInterceptor implements MethodInterceptor {
		
		@Around("com.gwtent.client.test.aop.AOPTestCase.Phone.*")
		public Object invoke(MethodInvocation invocation) throws Throwable {
			invocation.proceed();
			System.out.println("Do something in PhoneRedirectInterceptor...");
			return new Receiver("Alberto's Pizza Place");
		}
	}

	public void testAOPInner() {
		
		// BindRegistry.getInstance().bindInterceptor(
		// Matchers.subclassesOf(Phone.class),
		// Matchers.returns(Matchers.only(Receiver.class)),
		// new PhoneLoggerInterceptor(),
		// new PhoneRedirectInterceptor()
		// );

		// moduleDef.mapServlet(path, servletClass);
		// moduleDef = getModuleDef(logger, parts.moduleName); GWTShellServlet.java

		// delayTestFinish(50000);



//		System.out.println(Phone.class.getName());
//
//		Phone phone = (Phone) GWT.create(Phone.class);
//		Receiver auntJane = phone.call(123456789);
//		System.out.println(auntJane);
	}
	
	public void testAOPDirect(){
		Phone phone = new TestAOP_ForGen();
		Receiver auntJane = phone.call(123456789);
		System.out.println(auntJane);
	}

//	 public void testAOP(){
//	 System.out.println(TestAOP.class.getName());
//	 TestAOP test = (TestAOP)GWT.create(TestAOP.class);
//	 assertTrue(test.sayHello("James").equals("Hello: James"));
//	 }
}
