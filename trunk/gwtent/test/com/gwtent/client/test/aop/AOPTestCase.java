package com.gwtent.client.test.aop;



import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.gwtent.client.aop.Aspectable;
import com.gwtent.client.test.aop.Phone.Receiver;

public class AOPTestCase extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.gwtent.GwtEntTest";
	}

	protected void gwtSetUp() throws Exception {
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
		Phone phone = createPhone();
		Receiver auntJane = phone.call(123456789);
		System.out.println(auntJane);
		System.out.println("End testAOPInner");

	}
	
	
	public void testAfterThrowing(){
		Phone phone = createPhone();
		Receiver auntJane = phone.call(1); //this should log to after throwing
		System.out.println(auntJane);
	}
	
	public void testAOPDirect(){
//		Phone phone = new TestAOP_ForGen();
//		Receiver auntJane = phone.call(123456789);
//		System.out.println(auntJane);
	}

//	 public void testAOP(){
//	 System.out.println(TestAOP.class.getName());
//	 TestAOP test = (TestAOP)GWT.create(TestAOP.class);
//	 assertTrue(test.sayHello("James").equals("Hello: James"));
//	 }
	
	private Phone createPhone(){
		return createPhone(11223344);
	}
	
	private Phone createPhone(Number number){
		Phone result = (Phone)GWT.create(Phone.class);
		result.setNumber(number);
		return result;
	}
}
