package com.gwtent.client.test.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.gwtent.aop.matcher.Matcher;
import com.gwtent.aop.matcher.Matchers;
import com.gwtent.aop.matcher.MethodMatcher;
import com.gwtent.client.aop.AOPRegistor;
import com.gwtent.client.aop.Aspectable;
import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.aop.intercept.MethodInvocation;

public class AOPTestCase extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.gwtent.GwtEntTest";
	}

	protected void gwtSetUp() throws Exception {

	}

	public static class TA {
		public TA(String a) {

		}
	}

	public static class TB extends TA {

		public TB() {
			super("abcd");

		}

	}

	public static class Phone implements Aspectable {
		private static final Map<Number, Receiver> RECEIVERS = new HashMap<Number, Receiver>();

		static {
			RECEIVERS.put(123456789, new Receiver("Aunt Jane"));
			RECEIVERS.put(111111111, new Receiver("Santa"));
		}

		public Receiver call(Number number) {
			System.out.println("The call here...");
			return RECEIVERS.get(number);
		}
	}

	public static class Receiver {
		private final String name;

		public Receiver(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return getClass().getName() + "[name=" + name + "]";
		}
	}

	public static class PhoneLoggerInterceptor implements MethodInterceptor {
		public Object invoke(MethodInvocation invocation) throws Throwable {
			for (Object arg : invocation.getArguments()){
				System.out.println("Do something in PhoneLoggerInterceptor...");
				
				if (arg instanceof Number)
					System.out.println("CALL: " + arg);
			}

			return invocation.proceed();
		}
	}

	public static class PhoneRedirectInterceptor implements MethodInterceptor {
		public Object invoke(MethodInvocation invocation) throws Throwable {
			invocation.proceed();
			System.out.println("Do something in PhoneRedirectInterceptor...");
			return new Receiver("Alberto's Pizza Place");
		}
	}

//	public void testAOPInner() {
//		
//		// BindRegistry.getInstance().bindInterceptor(
//		// Matchers.subclassesOf(Phone.class),
//		// Matchers.returns(Matchers.only(Receiver.class)),
//		// new PhoneLoggerInterceptor(),
//		// new PhoneRedirectInterceptor()
//		// );
//
//		// moduleDef.mapServlet(path, servletClass);
//		// moduleDef = getModuleDef(logger, parts.moduleName); GWTShellServlet.java
//
//		// delayTestFinish(50000);
//
//		AOPRegistor.getInstance().bindInterceptor(
//				"com.gwtent.client.test.aop.TestMatcher", new PhoneLoggerInterceptor(),
//				new PhoneRedirectInterceptor());
//
//		System.out.println(Phone.class.getName());
//
//		Phone phone = (Phone) GWT.create(Phone.class);
//		Receiver auntJane = phone.call(123456789);
//		System.out.println(auntJane);
//		// assertTrue(test.sayHello("James").equals("Hello: James"));
//
//		// Injector i = Guice.createInjector(new PhoneModule());
//		// Phone phone = i.getInstance(Phone.class);
//		// Receiver auntJane = phone.call(123456789);
//		// System.out.println(auntJane);
//	}
	
	public void testAOPDirect(){
		Phone phone = new TestAOP_ForGen();
		Receiver auntJane = phone.call(123456789);
		System.out.println(auntJane);
	}

	// public void testAOP(){
	// System.out.println(TestAOP.class.getName());
	// TestAOP test = (TestAOP)GWT.create(TestAOP.class);
	// assertTrue(test.sayHello("James").equals("Hello: James"));
	// }
}
