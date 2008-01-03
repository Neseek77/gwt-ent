package com.gwtent.client.ui.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.gwtent.client.ui.model.Fields;
import com.gwtent.client.ui.validate.ValidateException;

public class UIFriendlyDefaultImpl implements UIFriendly {
	
	private Fields fields;
	
	public UIFriendlyDefaultImpl(){
		fields = new FieldsImpl();
	}

	public void wholeValidate(Object value) throws ValidateException {
		

	}

	public Fields getFields() {
		return fields;
	}

}
