package com.gwtent.client.test;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.junit.client.GWTTestCase;
import com.gwtent.aop.matcher.Matchers;
import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.aop.intercept.MethodInvocation;

public class AOPTestCase extends GWTTestCase{

	@Override
	public String getModuleName() {
		return "com.gwtent.GwtEnt";
	}
	
	public static class Phone {
	    private static final Map<Number, Receiver> RECEIVERS =
	        new HashMap<Number, Receiver>();
	    
	    static { 
	        RECEIVERS.put(123456789, new Receiver("Aunt Jane"));
	        RECEIVERS.put(111111111, new Receiver("Santa"));
	    }
	    
	    public Receiver call(Number number) {
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
	        return String.format("%s[name=%s]", getClass().getName(), name);
	    }
	}
	
	public static class PhoneLoggerInterceptor implements MethodInterceptor {
	    public Object invoke(MethodInvocation invocation) throws Throwable {
	        for (Object arg : invocation.getArguments())
	            if (arg instanceof Number)
	                System.out.println("CALL: "+arg);
	        
	        return invocation.proceed();
	    }
	}
	
	public static class PhoneRedirectInterceptor implements MethodInterceptor {
	    public Object invoke(MethodInvocation invocation) throws Throwable {
	        return new Receiver("Alberto's Pizza Place");
	    }
	}
	
	
	
	public void testAOP(){
		bindInterceptor(  
				            Matchers.subclassesOf(Phone.class),  
				            Matchers.returns(Matchers.only(Receiver.class)),  
				            new PhoneLoggerInterceptor(),  
				            new PhoneRedirectInterceptor()  
				        ); 
		
		Injector i = Guice.createInjector(new PhoneModule());  
		        Phone phone = i.getInstance(Phone.class);  
		        Receiver auntJane = phone.call(123456789);  
		        System.out.println(auntJane); 
	}
}
