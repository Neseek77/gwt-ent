package com.gwtent.client.test.aop;

import com.gwtent.client.aop.AspectException;
import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.impl.MethodImpl;

public class TestAOP_ForGen extends TestAOP {
	private final class Invocation implements MethodInvocation {
		private final MethodImpl method;
		public Invocation(MethodImpl method){
			this.method = method;
		}
		
		@Override
		public MethodImpl getMethod() {
			return method;
		}

		@Override
		public Object[] getArguments() {
			MethodImpl method = getMethod();
			return method.getParameters();
		}

		@Override
		public Object getThis() {
			return TestAOP_ForGen.this;
		}

		@Override
		public Object proceed() throws Throwable {
			throw new RuntimeException("No support yet.");
		}
	}

	public String sayHello(String guestName){
		MethodInterceptor interceptor = new AOPTestCase.PhoneLoggerInterceptor();
		try {
			return (String)interceptor.invoke(new Invocation(new MethodImpl(null, "")));
		} catch (Throwable e) {
			throw new AspectException(e);
		}
		//return super.sayHello(guestName);
	}
}
