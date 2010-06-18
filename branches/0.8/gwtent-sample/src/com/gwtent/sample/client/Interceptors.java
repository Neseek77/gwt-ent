package com.gwtent.sample.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.gwtent.client.aop.intercept.MethodInvocation;

import com.gwtent.sample.client.Phone.Receiver;

public class Interceptors {

	@Aspect
	public static class PhoneCallErrorLog{
		
		@AfterThrowing(pointcut="execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))",
				throwing="e")
		public void phoneCallErrorLog(MethodInvocation invocation, Throwable e){
			System.out.println("PhoneCallErrorLog: " + e.getMessage());
		}
	}

	@Aspect
	public static class PhoneLoggerInterceptor {
		
		//execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)
		@Before("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
		public void beforeCall(MethodInvocation invocation) {
			for (Object arg : invocation.getArguments()){
				System.out.println("Do something in PhoneLoggerInterceptor, Before...");
				
				if (arg instanceof Number)
					System.out.println("Call to: " + arg);
			}
		}
		
		@AfterReturning(pointcut = "execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))",
			returning = "returnValue")
		public void afterReturningCall(MethodInvocation invocation, Object returnValue) {
			System.out.println("Do something in PhoneLoggerInterceptor, AfterReturning...");
			
			if ((returnValue != null)){
				if (returnValue instanceof Number)
					System.out.println("Returning Number: " + returnValue.toString());
				else
					System.out.println("Returning Object: " + returnValue.toString());
			}else{
				System.out.println("Returning Object is NULL?");
			}
		}
		
		@After("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
		public void afterCall(MethodInvocation invocation) {				
			for (Object arg : invocation.getArguments()){
				System.out.println("Do something in PhoneLoggerInterceptor, After...");
				
				if (arg instanceof Number)
					System.out.println("After Call: " + arg);
			}
		}
	}
	
	
	@Aspect
	public static class PhoneBillingSystemInterceptor{
		private Map<Number, Long> callTime = new HashMap<Number, Long>();
		private Map<Number, Date> startCallTime = new HashMap<Number, Date>();
		
		@Before("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
		public void beforeCall(MethodInvocation invocation) {
			startCallTime.put(getPhoneNumber(invocation), new Date());
		}
		
		//We don't care what happened, we always earn money from client
		@After("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
		public void afterCall(MethodInvocation invocation, Object returnValue) {
			Number number = getPhoneNumber(invocation);
			Date startDate = startCallTime.get(number);
			if (startDate != null){
				Long allTime = callTime.get(number);
				if (allTime == null)
					allTime = 0L;
				allTime = allTime + ((new Date().getTime()) - startDate.getTime());
				callTime.put(number, allTime);
			}
				
			System.out.println("call time: " + callTime);
		}
		
		private Number getPhoneNumber(MethodInvocation invocation){
			return ((Phone)(invocation.getThis())).getNumber();
		}
	}

	@Aspect
	public static class PhoneNumberValidateInterceptor{
		//@Pointcut("args(java.lang.Number,..)")
		private void numberArgOperation(Number number){};
		
		//args not full support yet
		@Before("args(java.lang.Number,..)")
		//@Before("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
		public void validateNumberNotSupportYet(Number number) throws Throwable {
			System.out.println("Validate, you cann't dail to 0, current Number(most time it's null): " + number);
			if ((number != null) && (number.intValue() == 0)){
				throw new RuntimeException("You cann't dail to 0.");
			}
		}
		
		@Before("args(java.lang.Number,..)")
		//@Before("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
		public void validateNumber(MethodInvocation invocation) throws Throwable {
			Number number = null;
			for (Object obj : invocation.getArguments()){
				if (obj instanceof Number)
					number = (Number)obj;
			}
			if (number != null){
				System.out.println("Validate, you cann't dail to 0, current Number: " + number);
				if ((number != null) && (number.intValue() == 0)){
					throw new RuntimeException("You cann't dail to 0.");
				}
			}
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
	
}


