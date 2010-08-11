package com.gwtent.validate.server.constraints;

import java.math.BigDecimal;
import java.math.BigInteger;

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
			if ( value instanceof BigDecimal ) {
				return ( ( BigDecimal ) value ).compareTo( BigDecimal.valueOf( min ) ) != -1;
			}
			else if ( value instanceof BigInteger ) {
				return ( ( BigInteger ) value ).compareTo( BigInteger.valueOf( min ) ) != -1;
			}
			else {
				double doubleValue = value.doubleValue();
				return doubleValue >= min;
			}
		}
}
