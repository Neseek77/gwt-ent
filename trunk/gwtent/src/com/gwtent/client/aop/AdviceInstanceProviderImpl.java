package com.gwtent.client.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.AnnotationStore;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.Parameter;

/**
 * 1, Provid advice instance depend on @Aspect (singleton...)
 * 2, Adapte @After, @AfterReturning, @AfterThrowing, @Around,
 * 		@Before to MethodInterceptor
 * 
 * @author JLuo
 *
 */
public class AdviceInstanceProviderImpl implements AdviceInstanceProvider {

	
	public static class MethodInterceptorAdapter implements MethodInterceptor{

		private final Method method;
		private final Object aspectInstance;
		
		public MethodInterceptorAdapter(Method method, Object aspectInstance){
			this.method = method;
			this.aspectInstance = aspectInstance;
		}
		
		public Object invoke(MethodInvocation invocation) throws Throwable {
			Object[] args = ArgsGeneratorImpl.getInstance().createArgs(invocation, method);
			if (method.isAnnotationPresent(Around.class)){
				//if Around, put every thing to method, and let method choose how/when invoke next invocation
				//return method.invoke(aspectInstance, new Object[]{invocation});
				return method.invoke(aspectInstance, args);
			}else if (method.isAnnotationPresent(Before.class)){
				method.invoke(aspectInstance, args);
				return invocation.proceed();
			}
			
			throw new RuntimeException("Advice type not supported.");
		}
		
		
		public interface ArgsGenerator{
			/**
			 * Create Argument array for invoke adviced method
			 * Using a smart way. If "args" in AspectJ expression, using this
			 * If the parameter's type is unique, match the parameter 
			 *   by this type, no matter what's the parameter name
			 * if the parameter's type have more then one, check the 
			 *   parameter's name, only match if parameter name and type is the same
			 *   
			 * @param invocation MethodInvocation 
			 * @param method is the method will be invoked.
			 * @return Object[] args
			 */
			Object[] createArgs(MethodInvocation invocation, Method method);
		}
		
		public static class ArgsGeneratorImpl implements ArgsGenerator{

			private static ArgsGenerator instance = new ArgsGeneratorImpl();
			
			public static ArgsGenerator getInstance(){
				return instance;
			}
			
			public Object[] createArgs(MethodInvocation invocation, Method method) {
				Parameter[] params = method.getParameters();
				Object[] result = new Object[params.length];
				for (int i = 0; i < params.length; i++) {
					Parameter param = params[i];
					
					if (onlyOneByType(params, param)){
						result[i] = getArgByType_OnlyOne(invocation, param.getTypeName());
					}
				}
				
				return result;
			}
			
			private Object getArgByType_OnlyOne(MethodInvocation invocation, String typeName){
				for (Object obj : invocation.getArguments()){
					if (obj.getClass().getName().equals(typeName))
						return obj;
				}
				
				if(typeName.equals(MethodInvocation.class.getName()))
					return invocation;
				
				return null;
			}
			
			private boolean onlyOneByType(Parameter[] params, Parameter param){
				for (Parameter p : params){
					if ((p != param) && (p.getTypeName().equals(param.getTypeName()))){
						return false;
					}
				}
				return true;
			}
			
		}
		
	}
	
	
	/**
	 * Map<ClassType AspectClassType, Object AspectInstance>
	 */
	public Map<ClassType, Object> singletonAspects = new HashMap<ClassType, Object>();
	public Map<Method, MethodInterceptor> singletonInterceptors = new HashMap<Method, MethodInterceptor>();
	
	public MethodInterceptor getInstance(Method method) {
		MethodInterceptor result = singletonInterceptors.get(method);
		if (result == null){
			Object aspect = getAspectInstance(method.getEnclosingType());
			result = new MethodInterceptorAdapter(method, aspect);
			singletonInterceptors.put(method, result);
		}
		
		return result;
	}
	
	protected Object getAspectInstance(ClassType classType){
		Object result = singletonAspects.get(classType);
		
		if (result == null){
			AnnotationStore annotation = classType.getAnnotation(Aspect.class);
			if (annotation != null){
				if ((annotation.getValue("value") == "") || ("singleton".equalsIgnoreCase(annotation.getValue("value").toString()))){
					result = classType.findConstructor(new String[]{}).newInstance();
					singletonAspects.put(classType, result);
				}
				else
					throw new RuntimeException("Now just support singleton aspect.");
			}
			
		}
		
		return result;
	}

}
