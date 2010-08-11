package com.gwtent.validate.client.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Size;


/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 * <p/>
 * Check that a string length is between min and max
 */
public class SizeValidatorForString implements ConstraintValidator<Size, String> {
    private int min;
    private int max;

    /**
     * Configure the constraint validator based on the elements
     * specified at the time it was defined.
     *
     * @param constraint the constraint definition
     */
    public void initialize(Size constraint) {
        min = constraint.min();
        max = constraint.max();
    }

    /**
     * Validate a specified value.
     * returns false if the specified value does not conform to the definition
     *
     * @throws IllegalArgumentException if the object is not of type String
     */
		public boolean isValid(String value,
				ConstraintValidatorContext constraintValidatorContext) {
			if (value == null) return false;
      
      int length = value.length();
      return length >= min && length <= max;
		}
}