package com.gwtent.client.ui.model.value;

import com.gwtent.client.reflection.ClassType;

public interface ValueReflectionAware {

	public ClassType getClassType();
	
	public Object getPojo();

	public String getFieldName();

	public String getTypeName();

	public void setClassType(ClassType classType);

	public void setFieldName(String fieldName);

	public void setPojo(Object pojo);

	public void setTypeName(String typeName);
	
	
	/**
	 * call when complete setup all params above
	 */
	public void completeSetReflectionValues();

}
