# 1, Introduction #

> Showcase available at: [Gwtent\_Showcase](http://gwtent-showcase.appspot.com/)
> And a local runnable showcase included in download page.

> Now Aspect-Oriented Programming is available in GWT.

> The following advice type supported by GWTENT(Compatible with AspectJ annotation).
    1. @Around
    1. @Before
    1. @After
    1. @AfterReturning
    1. @AfterThrowing

  * Around advice: Advice that surrounds a join point such as a method invocation. This is the most powerful kind of advice. Around advice can perform custom behavior before and after the method invocation. It is also responsible for choosing whether to proceed to the join point or to shortcut the advised method execution by returning its own return value or throwing an exception.
  * Before advice: Advice that executes before a join point, but which does not have the ability to prevent execution flow proceeding to the join point (unless it throws an exception).
  * After (finally) advice: Advice to be executed regardless of the means by which a join point exits (normal or exceptional return).
  * After returning advice: Advice to be executed after a join point completes normally: for example, if a method returns without throwing an exception.
  * After throwing advice: Advice to be executed if a method exits by throwing an exception.

> And for pointcut, we support both Google Guice matcher classes and AspectJ expression(a subset of AspectJ expression)

> Please see [here](http://gwt-ent.googlecode.com/svn/trunk/gwtent_showcase/src/main/java/com/gwtent/showcase/client/aop/Interceptors.java) for all interceptors in showcase project.
> Please download the sample project[here](http://code.google.com/p/gwt-ent/downloads/list)


# 2, Pointcut #

## 2.1, Overview ##
Pointcut: A predicate that matches join points. Advice is associated with a pointcut expression and runs at any join point matched by the pointcut (for example, the execution of a method with a certain name). The concept of join points as matched by pointcut expressions is central to AOP: we support AspectJ pointcut language and Google Guice matcher classes.

### 2.1.1, Client-side code? ###
> Please note, All pointcut match code is runing at compile time, it's mean you can write truely java code in your matcher class and without any limit in GWT client-side code.
> In compile time and host-mode, GWTENT require AspectJ jars to dealwith AspectJ expression, but after your compile it, all are javascript.

## 2.2, AspectJ pointcut expression style ##
> to be continue

## 2.2, Google Guice matcher style ##
> We support "matchclass(the class name which implement com.gwtent.aop.matcher.ClassMethodMatcher)", for example:
```
	@Aspect
	public static class PhoneRedirectInterceptor {
		
		@Around("matchclass(com.gwtent.test.aspectj.TestMatcher)")
		public Object invoke(MethodInvocation invocation) throws Throwable {
			invocation.proceed();
			System.out.println("Do something in PhoneRedirectInterceptor...");
			return new Receiver("Alberto's Pizza Place");
		}
	}
```

the matcher class will looks like this:
```
public class TestMatcher implements ClassMethodMatcher {

	public Matcher<? super Class<?>> getClassMatcher() {
		return Matchers.subclassesOf(Phone.class);
	}

	public Matcher<? super Method> getMethodMatcher() {
		return Matchers.returns(Matchers.only(Phone.Receiver.class));
		//return Matchers.any();
	}

}
```

# 3, Advice #

## 3.1, Arguments Binding ##
  1. if there is "com.gwtent.client.aop.intercept.MethodInvocation" in args list, then invocation will assign to args.
  1. if source method and advice method have the same class type and this type just have one, then we assign this one to args
  1. Read settings in @AfterReturning
```
	 @AfterReturning(
	   pointcut="**",
	   returning="retVal"
	   )
	   public void afterReturning(Object retVal)
```
  1. Read settings in @AfterThrowing
```
	 @AfterThrowing(
	 	 throwing="e"
	 } 
	 public void afterThrowing(Throwable e)
```

## 3.2, @Around ##
> Advice that surrounds a join point such as a method invocation. This is the most powerful kind of advice. Around advice can perform custom behavior before and after the method invocation. It is also responsible for choosing whether to proceed to the join point or to shortcut the advised method execution by returning its own return value or throwing an exception.

### 3.2.1, Example ###
```
		@Around("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
		public Object invoke(MethodInvocation invocation) throws Throwable {
			invocation.proceed();
			System.out.println("Do something in PhoneRedirectInterceptor...");
			return new Receiver("Alberto's Pizza Place");
		}
```


## 3.3, @Before ##
> Advice that executes before a join point, but which does not have the ability to prevent execution flow proceeding to the join point (unless it throws an exception).
### 3.3.1, Example ###
```
		@Before("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
		public void beforeCall(MethodInvocation invocation) {
			for (Object arg : invocation.getArguments()){
				System.out.println("Do something in PhoneLoggerInterceptor, Before...");
				
				if (arg instanceof Number)
					System.out.println("Call to: " + arg);
			}
		}
```

## 3.4, @After ##
> After (finally) Advice to be executed regardless of the means by which a join point exits (normal or exceptional return).

### 3.4.1, Example ###
```
		@After("execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))")
		public void afterCall(MethodInvocation invocation) {				
			for (Object arg : invocation.getArguments()){
				System.out.println("Do something in PhoneLoggerInterceptor, After...");
				
				if (arg instanceof Number)
					System.out.println("After Call: " + arg);
			}
		}
```

## 3.5, @AfterReturning ##
> Advice to be executed after a join point completes normally: for example, if a method returns without throwing an exception.

### 3.5.1, Example ###
```
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
```

## 3.6, @AfterThrowing ##
> Advice to be executed if a method exits by throwing an exception.

### 3.6.1, Example ###
```
		@AfterThrowing(pointcut="execution(* com.gwtent.sample.client.Phone.call(java.lang.Number))",
				throwing="e")
		public void phoneCallErrorLog(MethodInvocation invocation, Throwable e){
			System.out.println("PhoneCallErrorLog: " + e.getMessage());
		}
```


# 4, The whole example #

## Class ##
The normal gwt class, for now you need implement "Aspectable" mark interface
```

public class Phone implements Aspectable {
	private static final Map<Number, Receiver> RECEIVERS = new HashMap<Number, Receiver>();

	static {
		RECEIVERS.put(123456789, new Receiver("Aunt Jane"));
		RECEIVERS.put(111111111, new Receiver("Santa"));
	}
	
	/**
	 * the phone number, like your home number
	 */
	private Number number;

	public Receiver call(Number number) {
		System.out.println("The call here...");
		Receiver result = RECEIVERS.get(number);
		if (result != null)
			return result;
		else
			throw new NumberNotFoundException("Can't  found receiver, number: " + number);
	}
	
	public Receiver call(String number){
		System.out.println("The call here...");
		return RECEIVERS.get(111111111);
	}
	
	public String toString(){
		return super.toString();
	}
	

	public void setNumber(Number number) {
		this.number = number;
	}

	public Number getNumber() {
		return number;
	}
	
	public static class NumberNotFoundException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public NumberNotFoundException(String msg){
			super(msg);
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
```


## Aspect Classes ##
```

	@Aspect
	public static class PhoneLoggerInterceptor {
		
		//execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)
		@Before("execution(* com.gwtent.client.test.aop.Phone.call(java.lang.Number))")
		public void beforeCall(MethodInvocation invocation) {
			for (Object arg : invocation.getArguments()){
				System.out.println("Do something in PhoneLoggerInterceptor, Before...");
				
				if (arg instanceof Number)
					System.out.println("Call to: " + arg);
			}
		}
		
		@AfterReturning(pointcut = "execution(* com.gwtent.client.test.aop.Phone.call(java.lang.Number))",
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
	public static class PhoneRedirectInterceptor {
		
		@Around("execution(* com.gwtent.client.test.aop.Phone.call(java.lang.Number))")
		public Object invoke(MethodInvocation invocation) throws Throwable {
			invocation.proceed();
			System.out.println("Do something in PhoneRedirectInterceptor...");
			return new Receiver("Alberto's Pizza Place");
		}
	}
```

## Using it ##

```
  public void testAOP(){
  	Phone phone = (Phone) GWT.create(Phone.class);
	Receiver auntJane = phone.call(123456789);
	System.out.println(auntJane);
  }
```

### The Result - System Output ###
```
Do something in PhoneLoggerInterceptor, Before...
Call to: 123456789
Validate, you cann't dail to 0, current Number(most time it's null): null
The call here...
Do something in PhoneRedirectInterceptor...
Do something in PhoneLoggerInterceptor, After...
After Call: 123456789
Do something in PhoneLoggerInterceptor, AfterReturning...
Returning Object: com.gwtent.sample.client.Phone$Receiver[name=Alberto's Pizza Place]
com.gwtent.sample.client.Phone$Receiver[name=Alberto's Pizza Place]
```