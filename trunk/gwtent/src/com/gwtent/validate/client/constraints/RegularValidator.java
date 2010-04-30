package com.gwtent.validate.client.constraints;

import javax.validation.ConstraintValidator;

public class RegularValidator extends RegularBasedValidator<Regular, Object> implements ConstraintValidator<Regular, Object> {

	private String regex;

	public void initialize(Regular r) {
		regex = r.regex();
	}
	public String getRegex() {
		return regex;
	};
}
