package com.gwtent.validate.client.constraints.impl;

import javax.validation.ConstraintValidator;

import com.gwtent.validate.client.constraints.Email;
import com.gwtent.validate.client.constraints.RegularBasedValidator;

public class EmailValidator extends RegularBasedValidator<Email, Object> implements ConstraintValidator<Email, Object> {

	private final String regex = "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+";

	public void initialize(Email r) {
		
	}

	protected String getRegex() {
		return regex;
	}

}
