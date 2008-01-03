package com.gwtent.client.ui.model.impl;

import java.util.Map;

import com.gwtent.client.ui.model.Fields;
import com.gwtent.client.ui.validate.ValidateException;

public interface UIFriendly {
	
	public Fields getFields();
	
	/**
	 * validate all model fields
	 * @param value
	 * @throws ValidateException
	 */
	public void wholeValidate(Object value) throws ValidateException;
}
