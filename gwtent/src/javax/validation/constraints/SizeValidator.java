package javax.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.StandardConstraint;
import javax.validation.StandardConstraintDescriptor;


/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 * <p/>
 * Check that a string length is between min and max
 */
public class SizeValidator implements ConstraintValidator<Size, String>, StandardConstraint {
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


    /** Returns the standard constraint descriptor in accordance with the max constraint */
    public StandardConstraintDescriptor getStandardConstraintDescriptor() {
        return new StandardConstraintDescriptor() {
            public Integer getLength() {
                if (max == Integer.MAX_VALUE) {
                    return null;
                } else {
                    return max;
                }
            }
        };
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