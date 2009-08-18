package com.gwtent.client.validate.constraints;

import javax.validation.ConstraintValidator;

public class EmailValidator extends RegularBasedValidator<Email, Object> implements ConstraintValidator<Email, Object> {

	private final String regex = "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+";

	public void initialize(Email r) {
		
	}

	protected String getRegex() {
		return regex;
	}

}
