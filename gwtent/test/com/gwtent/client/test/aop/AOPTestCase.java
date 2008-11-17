package com.gwtent.client.test.aop;


import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.gwtent.client.aop.Aspectable;
import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.test.aop.Phone.Receiver;

public class AOPTestCase extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.gwtent.GwtEntTest";
	}

	protected void gwtSetUp() throws Exception {
	}

	@Aspect
	public static class PhoneLoggerInterceptor {
		
		//execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)
		@Around("execution(* com.gwtent.client.test.aop.Phone.call(java.lang.Number))")
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
	public static class PhoneRedirectInterceptor {
		
		@Around("execution(* com.gwtent.client.test.aop.Phone.call(java.lang.Number))")
		public Object invoke(MethodInvocation invocation) throws Throwable {
			invocation.proceed();
			System.out.println("Do something in PhoneRedirectInterceptor...");
			return new Receiver("Alberto's Pizza Place");
		}
	}
	
	@Aspect
	public static class PhoneTestInterceptor{
		@Pointcut("args(java.lang.Number,..)")
		private void numberArgOperation(Number number){};
		
		@Before("args(java.lang.Number,..)")
		public void validateNumber(Number number) throws Throwable {
			if ((number != null) && (number.intValue() == 0)){
				throw new RuntimeException("You cann't dail to 0.");
			}
		}
	}
	
	public void testAspectInterface(){
		try{
			Aspectable obj = (Aspectable)GWT.create(Aspectable.class);
			assertTrue("Should get error.", false);
		}catch (Throwable e){
			assertTrue(true);
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
		Phone phone = (Phone) GWT.create(Phone.class);
		Receiver auntJane = phone.call(123456789);
		System.out.println(auntJane);
		System.out.println("End testAOPInner");
		
		phone = (Phone) GWT.create(Phone.class);
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
