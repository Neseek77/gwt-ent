package javax.validation;

import java.lang.annotation.Annotation;

import com.gwtent.reflection.client.annotations.Reflect_Full;

/**
 * Defines the logic to validate a given constraint A for a given object type T.
 * Implementations must comply to the following restriction: T must resolve to a
 * non parameterized type
 * 
 * @Reflect_Full is need for gwt client code
 * 
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */

public interface ConstraintValidator<A extends Annotation, T> {
	/**
	 * Initialize the validator in preparation for isValid calls. The onstraint
	 * annotation for a given constraint declaration is passed. Constraint
	 * Definition 1.0.CR1 Proposed Final Draft 12
	 * <p/>
	 * This method is guaranteed to be called before any of the other Constraint
	 * implementation methods
	 * 
	 * @param constraintAnnotation
	 *          annotation instance for a given constraint declaration
	 */
	void initialize(A constraintAnnotation);

	/**
	 * Implement the validation logic. <code>value</code> state must not be
	 * altered.
	 * 
	 * @param value
	 *          object to validate
	 * @param constraintValidatorContext
	 *          context in which the constraint is evaluated
	 * 
	 * @return false if <code>value</code> does not pass the constraint
	 */
	boolean isValid(T value, ConstraintValidatorContext constraintValidatorContext);
}