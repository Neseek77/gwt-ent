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
