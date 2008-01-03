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
