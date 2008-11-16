package com.gwtent.client.test.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwtent.client.aop.AdviceInstanceProvider;
import com.gwtent.client.aop.AspectException;
import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.aop.intercept.impl.MethodInterceptorFinalAdapter;
import com.gwtent.client.aop.intercept.impl.MethodInvocationLinkedAdapter;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.TypeOracle;

public class TestAOP_ForGen extends Phone {
	private static class InterceptorMap{
		static Map<Method, List<Method>> interceptors = new HashMap<Method, List<Method>>();
		//static List<String> matcherClassNames = null;

		static {
			ClassType aspectClass = null;
			Method method = null;
			
		  //loop... for every aspected method
			List<Method> matchAdvices = new ArrayList<Method>();
			
			aspectClass = TypeOracle.Instance.getClassType(AOPTestCase.PhoneLoggerInterceptor.class);
			method = aspectClass.findMethod("invoke", new String[]{"com.gwtent.client.aop.intercept.MethodInvocation"});
			matchAdvices.add(method);
			
			aspectClass = TypeOracle.Instance.getClassType(AOPTestCase.PhoneRedirectInterceptor.class);
			method = aspectClass.findMethod("invoke", new String[]{"com.gwtent.client.aop.intercept.MethodInvocation"});
			matchAdvices.add(method);

			interceptors.put(classType.findMethod("call", new String[]{"java.lang.Number"}), matchAdvices);
			//end loop...
		}
	}
	
	private static final ClassType classType = TypeOracle.Instance.getClassType(Phone.class);
	
	private final MethodInvocationLinkedAdapter Ivn_call;
	
	public TestAOP_ForGen(){
		
		Method method = null;
		//Do loop...
		method = classType.findMethod("call", new String[]{"java.lang.Number"});
		Ivn_call = createMethodInvocationChain(method);
	}


	//TODO How To handle error?
	public Receiver call(Number number) {
		if (Ivn_call.getCurrentInterceptor() instanceof MethodInterceptorFinalAdapter){
			return super.call(number);
		}
		
		Object[] args = new Object[]{number};
		Ivn_call.reset(args);
		try {
			return (Receiver) Ivn_call.proceed();
		} catch (Throwable e) {
			throw new AspectException(e);
		}
	}
	
	private MethodInvocationLinkedAdapter createMethodInvocationChain(Method method) {
		List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
		for (Method adviceMethod : InterceptorMap.interceptors.get(method)){
			interceptors.add(AdviceInstanceProvider.INSTANCE.getInstance(adviceMethod));
		}
		interceptors.add(new MethodInterceptorFinalAdapter());
		return new MethodInvocationLinkedAdapter(method, this, interceptors);
	}
	
}
