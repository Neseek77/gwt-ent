package com.gwtent.client.validate.constraints;

//import java.lang.reflect.Array;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegularValidator implements ConstraintValidator<Regular, String> {

	private String regex;

	public void initialize(Regular r) {
		regex = r.regex();
	}

	public boolean isValid(String value,
			ConstraintValidatorContext constraintValidatorContext) {
		return isValid(value, regex);
	}

	protected native boolean isValid(String s, String regex)/*-{
	 	return new RegExp(regex).test(s);
	 }-*/;

	public String getRegex() {
		return regex;
	};
}
