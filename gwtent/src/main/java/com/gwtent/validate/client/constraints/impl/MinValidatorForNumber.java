package com.gwtent.validate.client.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Min;


public class MinValidatorForNumber implements ConstraintValidator<Min, Number>{
    private long min;

    public void initialize(Min constraintAnnotation) {
        min = constraintAnnotation.value();
    }

		public boolean isValid(Number value,
				ConstraintValidatorContext constraintValidatorContext) {
			if ( value == null ) {
				return true;
			}
			
			double doubleValue = value.doubleValue();
				return doubleValue >= min;
		}
}
