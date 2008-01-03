package com.gwtent.client.ui.model;

import com.gwtent.client.ui.validate.Validate;

/**
 * every type have their own edit type
 * we have a registry record relation between type and edit type
 * registry can override that other can control the edit type 
 * 
 * @author James Luo
 * 2007-12-25 下午08:30:11
 *
 */
public interface Value {
	public boolean getReadOnly();
	public void setReadOnly(boolean readOnly);
	
	public String getAsString();
	
	public Validate getValidate();
	
	public Object getValue();
	public void setValue(Object value);
	
	public String getTypeName();
	public void setTypeName(String typeName);

}
