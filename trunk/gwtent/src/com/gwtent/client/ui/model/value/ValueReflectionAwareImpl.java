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


package com.gwtent.client.ui.model.value;

import com.gwtent.client.reflection.ClassType;

public class ValueReflectionAwareImpl extends ValueDefaultImpl implements ValueReflectionAware{
	
	private Object pojo;
	private ClassType classType;
	private String fieldName;
	private String typeName;
	
	
	public ValueReflectionAwareImpl(Object pojo, ClassType classType, String fieldName, String typeName){
		this.pojo = pojo;
		this.classType = classType;
		this.fieldName = fieldName;
		this.typeName = typeName;
	}
	

	public ClassType getClassType() {
		return classType;
	}

	public Object getPojo() {
		return pojo;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setPojo(Object pojo) {
		this.pojo = pojo;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public void completeSetReflectionValues() {
		// TODO Auto-generated method stub
		
	}
	

}
