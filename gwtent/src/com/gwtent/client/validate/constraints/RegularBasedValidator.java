package com.gwtent.client.validate.constraints;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public abstract class RegularBasedValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {
	protected abstract String getRegex();
	
	public boolean isValid(Object value,
			ConstraintValidatorContext constraintValidatorContext) {
		if (value == null)
			return isValid("", getRegex());
		else
  		return isValid(value.toString(), getRegex());
	}

	protected native boolean isValid(String s, String regex)/*-{
	 	return new RegExp(regex).test(s);
	 }-*/;

}
