package com.gwtent.client.ui.model;

public interface Field {

	public Value getValue();

	/**
	 * Caption in this implement support HTML
	 * 
	 * @return
	 */
	public String getCaption();

	public boolean getRequire();

	public String getDesc();

	public void setCaption(String caption);

	public void setDesc(String desc);

	public void setRequire(boolean require);

	public void setValue(Value value);

	//	
	//	
	// public void validate(Object value) throws ValidateException;
}
