package com.gwtent.client.ui.editor;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.model.Value;
import com.gwtent.client.ui.validate.ValidateCallBack;

public interface Editor {
	public Value getValue();
	public void setValue(Value value);
	
	public ValidateCallBack getValidateCallBack();
	public void setValidateCallBack(ValidateCallBack callBack);
	
	public Widget getWidget();
}
