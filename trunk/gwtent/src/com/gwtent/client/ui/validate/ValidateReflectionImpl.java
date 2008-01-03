package com.gwtent.client.ui.validate;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.ui.ClassTypeHelper;

public class ValidateReflectionImpl implements Validate{

//	private ClassType classType;
	private String functionName;
	private Object instance;
	
	private Method syncMethod = null;
	private Method asyncMethod = null;
	
	public ValidateReflectionImpl(ClassType classType, Object instance, String functionName){
		//this.classType = classType;
		this.instance = instance;
		this.functionName = functionName;
		
		syncMethod = ClassTypeHelper.findSyncValidateMethod(classType, functionName);
		asyncMethod = ClassTypeHelper.findAsyncValidateMethod(classType, functionName);
	}
	
	public void AsyncValidate(Object value, ValidateCallBack callBack) throws ValidateException {
		if (asyncMethod != null){
			asyncMethod.invoke(instance, functionName, new Object[]{value, callBack});
		}
	}

	public void SyncValidate(Object value) throws ValidateException {
		if (syncMethod != null){
			syncMethod.invoke(instance, functionName, new Object[]{value});
		}
		
	}

}
