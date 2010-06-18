package com.gwtent.showcase.client.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.gwtent.aop.client.intercept.MethodInvocation;
import com.gwtent.showcase.client.aop.Phone.Receiver;

public class Interceptors {
	
	public static interface Log{
		void println(String str);
	}
	
	private static Log log;
	public static void setLog(Log log){
		Interceptors.log = log;
	}
	public static Log getLog(){
		if (log == null)
			throw new RuntimeException("Please setup log first.");
		
		return Interceptors.log;
	}

	@Aspect
	public static class PhoneCallErrorLog{
		
		@AfterThrowing(pointcut="execution(* com.gwtent.showcase.client.aop.Phone.call(java.lang.Number))",
				throwing="e")
		public void phoneCallErrorLog(MethodInvocation invocation, Throwable e){
			Interceptors.getLog().println("PhoneCallErrorLog: " + e.getMessage());
		}
	}

	@Aspect
	public static class PhoneLoggerInterceptor {
		
		//execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)
		@Before("execution(* com.gwtent.showcase.client.aop.Phone.call(java.lang.Number))")
		public void beforeCall(MethodInvocation invocation) {
			for (Object arg : invocation.getArguments()){
				Interceptors.getLog().println("Do something in PhoneLoggerInterceptor, Before...");
				
				if (arg instanceof Number)
					Interceptors.getLog().println("Call to: " + arg);
			}
		}
		
		@AfterReturning(pointcut = "execution(* com.gwtent.showcase.client.aop.Phone.call(java.lang.Number))",
			returning = "returnValue")
		public void afterReturningCall(MethodInvocation invocation, Object returnValue) {
			Interceptors.getLog().println("Do something in PhoneLoggerInterceptor, AfterReturning...");
			
			if ((returnValue != null)){
				if (returnValue instanceof Number)
					Interceptors.getLog().println("Returning Number: " + returnValue.toString());
				else
					Interceptors.getLog().println("Returning Object: " + returnValue.toString());
			}else{
				Interceptors.getLog().println("Returning Object is NULL?");
			}
		}
		
		@After("execution(* com.gwtent.showcase.client.aop.Phone.call(java.lang.Number))")
		public void afterCall(MethodInvocation invocation) {				
			for (Object arg : invocation.getArguments()){
				Interceptors.getLog().println("Do something in PhoneLoggerInterceptor, After...");
				
				if (arg instanceof Number)
					Interceptors.getLog().println("After Call: " + arg);
			}
		}
	}
	
	
	@Aspect
	public static class PhoneBillingSystemInterceptor{
		private Map<Number, Long> callTime = new HashMap<Number, Long>();
		private Map<Number, Date> startCallTime = new HashMap<Number, Date>();
		
		@Before("execution(* com.gwtent.showcase.client.aop.Phone.call(java.lang.Number))")
		public void beforeCall(MethodInvocation invocation) {
			startCallTime.put(getPhoneNumber(invocation), new Date());
		}
		
		//We don't care what happened, we always earn money from client
		@After("execution(* com.gwtent.showcase.client.aop.Phone.call(java.lang.Number))")
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
				
			Interceptors.getLog().println("call time: " + callTime);
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
		//@Before("execution(* com.gwtent.sample.client.aop.Phone.call(java.lang.Number))")
		public void validateNumberByDirectParameter(Number number) throws Throwable {
			if (number != null){
				Interceptors.getLog().println("Validate here, you can got the number directly, if you dail to president, direct hang, current Number: " + number);
				if ((number != null) && (number.intValue() == 0)){
					throw new RuntimeException("You cann't dail to 0.");
				}
			}
		}
		
		@Before("args(java.lang.Number,..)")
		//@Before("execution(* com.gwtent.sample.client.aop.Phone.call(java.lang.Number))")
		public void validateNumber(MethodInvocation invocation) throws Throwable {
			Number number = null;
			for (Object obj : invocation.getArguments()){
				if (obj instanceof Number)
					number = (Number)obj;
			}
			if (number != null){
				Interceptors.getLog().println("Validate here, if you dail to president, direct hang, current Number: " + number);
				if ((number != null) && (number.intValue() == 0)){
					throw new RuntimeException("You cann't dail to 0.");
				}
			}
		}
	}

	@Aspect
	public static class PhoneRedirectInterceptor {
		
		@Around("execution(* com.gwtent.showcase.client.aop.Phone.call(java.lang.Number))")
		public Object invoke(MethodInvocation invocation) throws Throwable {
			Object result = invocation.proceed();
			Number number = null;
			for (Object obj : invocation.getArguments()){
				if (obj instanceof Number)
					number = (Number)obj;
			}
			Interceptors.getLog().println("Do something in PhoneRedirectInterceptor...");
			if (number != null){
				if (number.equals(1101010)){
					Interceptors.getLog().println("Hmm, call to brother Laden? that's not good, redirect to our fake Laden");
					return new Receiver("Fake Laden from FBI");
				}else if (number.equals(1111111)){
					Interceptors.getLog().println("Hmm, call to Santa? He is sleeping, a Pizza is a good idea.");
					return new Receiver("Alberto's Pizza Place");
				}
			}
			return result;
		}
	}
	
	
	
	@Target({ElementType.METHOD, ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface AOPTestAnnotation{
		public String value();
	}
	
	@Aspect
	public static class PhoneTestAnnotation{
		@Before("@annotation(com.gwtent.showcase.client.aop.Interceptors.AOPTestAnnotation)")
		public void testPhoneAnnotation(MethodInvocation invocation) throws Throwable {
			Number number = null;
			for (Object obj : invocation.getArguments()){
				if (obj instanceof Number)
					number = (Number)obj;
			}
			
			Interceptors.getLog().println("annotation match express here, you called to " + number);
		}
	}
	
	@Aspect
	public static class PhoneTestMatchClass{
		@Before("matchclass(com.gwtent.showcase.aop.guicematcher.TestMatcher)")
		public void testPhoneAnnotation(MethodInvocation invocation) throws Throwable {
			Number number = null;
			for (Object obj : invocation.getArguments()){
				if (obj instanceof Number)
					number = (Number)obj;
			}
			
			Interceptors.getLog().println("Guice Matcher class here: you called to " + number);
		}
	}
	
}


