/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


package com.gwtent.ui.client.validate;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Method;
import com.gwtent.ui.client.ClassTypeHelper;

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
			asyncMethod.invoke(instance, new Object[]{value, callBack});
		}
	}

	public void SyncValidate(Object value) throws ValidateException {
		if (syncMethod != null){
			syncMethod.invoke(instance, new Object[]{value});
		}
		
	}

}
