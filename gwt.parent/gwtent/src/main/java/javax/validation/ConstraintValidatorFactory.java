package javax.validation;

/**
 * Instantiate a <code>ConstraintValidator</code> instance from its class. The
 * <code>ConstraintValidatorFactory</code> is <b>not</b> responsible for calling
 * {@link ConstraintValidator#initialize(java.lang.annotation.Annotation)}.
 * 
 * @author Dhanji R. Prasanna
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface ConstraintValidatorFactory {
	/**
	 * @param key
	 *          The class of the constraint validator to instantiate.
	 * 
	 * @return A constraint validator instance of the specified class.
	 */
	<T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key);
}
