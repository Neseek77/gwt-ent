package com.gwtent.client.ui.editor;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.model.Value;
import com.gwtent.client.ui.validate.ValidateCallBack;

public abstract class AbstractEditor implements Editor {
	
	private Value value;
	private ValidateCallBack validateCallBack;

	public AbstractEditor(Value value){
		this.value = value;
	}
	
	public Value getValue() {
		return value;
	}

	public abstract Widget getWidget();

	public void setValue(Value value) {
		this.value = value;
		
	}
	
	public ValidateCallBack getValidateCallBack(){
		return validateCallBack;
	}
	
	
	public void setValidateCallBack(ValidateCallBack callBack){
		this.validateCallBack = callBack;
	}

}
