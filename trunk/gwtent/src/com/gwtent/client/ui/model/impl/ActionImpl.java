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


package com.gwtent.client.ui.model.impl;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.ui.model.Action;
import com.gwtent.client.ui.model.ActionCallBack;

public class ActionImpl implements Action {

	private ClassType classType;
	private Object instance;
	private String actionName;
	private Method actionMethod;
	private Method asyncActionMethod;
	private String caption;
	
	
	public ActionImpl(ClassType classType, Object instance, String actionName){
		this.classType = classType;
		this.instance = instance;
		
		this.actionName = actionName;
		
		
		actionMethod = classType.findMethod(actionName, new String[]{});
//		if (actionMethod == null)
//			actionMethod = classType.findMethod(actionName, new String[]{ClassTypeHelper.OBJECT_CLASS_NAME});
		
		asyncActionMethod = classType.findMethod(actionName, new String[]{ActionCallBack.CLASSNAME});
//		if (asyncActionMethod == null){
//			asyncActionMethod = classType.findMethod(actionName, new String[]{ClassTypeHelper.OBJECT_CLASS_NAME, ActionCallBack.CLASSNAME});
//		}
		
		findCaption();
	}
	
	protected void findCaption(){
		if (actionMethod != null){
			//TODO MetaData
			//if (ClassTypeHelper.getAllMetaData(actionMethod, ClassTypeHelper.ACTION_CPATION_METADATA).length() > 0){
			//	this.caption = ClassTypeHelper.getAllMetaData(actionMethod, ClassTypeHelper.ACTION_CPATION_METADATA);
			//}
		}
		
		if (this.caption.length() <= 0){
			if (asyncActionMethod != null){
				//TODO MetaData
				//if (ClassTypeHelper.getAllMetaData(asyncActionMethod, ClassTypeHelper.ACTION_CPATION_METADATA).length() > 0){
				//	this.caption = ClassTypeHelper.getAllMetaData(asyncActionMethod, ClassTypeHelper.ACTION_CPATION_METADATA);
				//}
			}
		}
	}
	
	public void doAction(Object instance) {
		//None Param or Have One Param
		Method method = classType.findMethod(actionName, new String[]{});
		if (actionMethod != null){
			method.invoke(instance, new Object[]{});
		}
	}

	public void doAsyncAction(Object instance, ActionCallBack callBack) {
		if (asyncActionMethod != null){
			asyncActionMethod.invoke(instance, new Object[]{callBack});
		}
	}

	public String getCaption() {
		return caption;
	}

}
