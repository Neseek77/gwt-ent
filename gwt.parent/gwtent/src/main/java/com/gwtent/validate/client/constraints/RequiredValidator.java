package com.gwtent.validate.client.constraints;

//import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.StandardConstraint;
import javax.validation.StandardConstraintDescriptor;

public class RequiredValidator implements
		ConstraintValidator<Required, Object>, StandardConstraint {
	public void initialize(Required constraintAnnotation) {
		// do nothing
	}

	public StandardConstraintDescriptor getStandardConstraintDescriptor() {
		return new StandardConstraintDescriptor() {
			@Override
			public Boolean getNullability() {
				return false;
			}
		};
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
