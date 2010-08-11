package com.gwtent.validate.client.constraints.impl;

//import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gwtent.validate.client.constraints.NotEmpty;

/**
 * Description: Check the non emptyness of the element.
 * 
 * <pre>
 * --
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --
 * </pre>
 **/
public class NotEmptyValidator implements
		ConstraintValidator<NotEmpty, Object> {
	public void initialize(NotEmpty constraintAnnotation) {
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
			return ((String) value).length() > 0;
		}
	}
}
