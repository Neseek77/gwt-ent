package com.gwtent.showcase.client.validate;


import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MyDOBValidator implements
		ConstraintValidator<MyDOB, Date> {
	public void initialize(MyDOB constraintAnnotation) {
		// do nothing
	}

	public boolean isValid(Date value,
			ConstraintValidatorContext constraintValidatorContext) {
		if (value == null)
			return false;
		
		Date now = new Date();
		now.setYear(now.getYear() - 13);
		return now.compareTo(value) >= 0;
	}
}
