package com.gwtent.client.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.AnnotationStore;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Method;

public class AdviceInstanceProviderImpl implements AdviceInstanceProvider {

	
	public class MethodInterceptorAdapter implements MethodInterceptor{

		private final Method method;
		private final Object aspectInstance;
		
		public MethodInterceptorAdapter(Method method, Object aspectInstance){
			this.method = method;
			this.aspectInstance = aspectInstance;
		}
		
		public Object invoke(MethodInvocation invocation) throws Throwable {
			if (method.isAnnotationPresent(Around.class)){
				return method.invoke(aspectInstance, new Object[]{invocation});
			}
			
			throw new RuntimeException("Advice type not supported.");
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
