/*
 * GwtEnt - Gwt ent library.
 * 
 * Copyright (c) 2007, James Luo(JamesLuo.au@gmail.com)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
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
