package com.gwtent.client.ui.model;

import java.util.Iterator;


public interface Fields {
	//not necessary for ui model
//	public void addFieldInfo(String fieldName, FieldInfo fieldInfo);
//	public FieldInfo addFieldInfo(String fieldName, String caption, boolean require, String desc);
//	public FieldInfo addFieldInfo(String fieldName, String caption, boolean require, String desc, Validate validate);
	
//	public Field getField(String fieldName);
//	public Set getFieldNames();
	
	public void addField(Field field);
	
	/**
	 * iterator of field
	 * @return
	 */
	public Iterator iterator();
	
	public String getCaption();
	public void setCaption(String caption);
}
