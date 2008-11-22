package com.gwtent.client.aop.advice;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;

import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.AnnotationStore;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.Parameter;

public class ArgsBinderImpl implements ArgsBinder{

	private static ArgsBinder instance = new ArgsBinderImpl();
	
	private Map<String, ArgsBinder> argsBinderHanders = new HashMap<String, ArgsBinder>();
	
	public static ArgsBinder getInstance(){
		return instance;
	}
	
	/**
	 * Object[] invocation.getArguments()
	 */
	public Object[] createArgs(MethodInvocation invocation, Method method, 
			Object returnValue, Throwable throwingValue) {
		Parameter[] params = method.getParameters();
		Object[] result = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			Parameter param = params[i];
			
			if (onlyOneByType(params, param)){
				result[i] = getArgByType_OnlyOne(invocation, param.getTypeName());
			}
		}
		
		int paramIndex = this.getReturningParamIndex(method);
		if (paramIndex >= 0)
			result[paramIndex] = returnValue;
		
		paramIndex = this.getAfterThrowingParamIndex(method);
		if (paramIndex >= 0)
			result[paramIndex] = throwingValue;
		
		return result;
	}
	
	public void addArgsBinderHander(String ident, ArgsBinder argsBinder){
		argsBinderHanders.put(ident, argsBinder);
	}
	
	/**
	 * 
	 * @AfterReturning(
	 *   pointcut="**",
	 *   returning="retVal"
	 * )
	 * public void afterReturning(Object retVal)
	 * 
	 * 
	 * @param paramName
	 * @param value
	 */
	private int getReturningParamIndex(Method method){
		AnnotationStore annotation = method.getAnnotation(AfterReturning.class);
		if (annotation != null){
			String paramName = annotation.getValue("returning").toString();
			return getParamIndexByName(method, paramName);
		}

		return -1;	
	}
	
	/**
	 * @AfterThrowing(
	 * 	 throwing="e"
	 * } 
	 * public void afterThrowing(Throwable e)
	 * 
	 * @param method
	 * @return
	 */
	private int getAfterThrowingParamIndex(Method method){
		AnnotationStore annotation = method.getAnnotation(AfterThrowing.class);
		if (annotation != null){
			String paramName = annotation.getValue("throwing").toString();
			return getParamIndexByName(method, paramName);
		}
		
		return -1;
	}
	
	private int getParamIndexByName(Method method, String paramName){
		Parameter[]	params = method.getParameters();
		for (int i = 0; i < params.length; i++) {
			if (paramName.equals(params[i].getName()))
				return i;
		}
		
		return -1;
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
	
	/**
	 * Go through all Parameter[] to see if just one parameter 
	 * have the same type of Parameter param.
	 * @param params
	 * @param param
	 * @return
	 */
	private boolean onlyOneByType(Parameter[] params, Parameter param){
		for (Parameter p : params){
			if ((p != param) && (p.getTypeName().equals(param.getTypeName()))){
				return false;
			}
		}
		return true;
	}

}
