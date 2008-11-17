package com.gwtent.client.aop.advice;

import com.gwtent.client.aop.advice.AbstractAdvice.ArgsBinder;
import com.gwtent.client.aop.intercept.MethodInvocation;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.Parameter;

public class ArgsGeneratorImpl implements ArgsBinder{

	private static ArgsBinder instance = new ArgsGeneratorImpl();
	
	public static ArgsBinder getInstance(){
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
