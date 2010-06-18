package com.gwtent.sample.client;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.sample.client.Phone.Receiver;

public class Advices {

	@Aspect
	public static class PhoneLoggerInterceptor {
		
		//execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)
		@Around("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
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
		
		@Around("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
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
}
