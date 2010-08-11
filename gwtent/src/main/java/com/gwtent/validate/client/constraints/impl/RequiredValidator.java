package com.gwtent.validate.client.constraints.impl;

//import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gwtent.validate.client.constraints.Required;

public class RequiredValidator implements
		ConstraintValidator<Required, Object> {
	public void initialize(Required constraintAnnotation) {
		// do nothing
	}

	public boolean isValid(Object value,
			ConstraintValidatorContext constraintValidatorContext) {
		if (value == null)
			return false;
		// if (value.getClass().isArray()) {
		// return Array.getLength(value) > 0;
		// } else
		if (value instanceof Collection) {
			return ((Collection) value).size() > 0;
		} else if (value instanceof Map) {
			return ((Map) value).size() > 0;
		} else {
			return (value.toString().length() > 0);
		}
	}
}
