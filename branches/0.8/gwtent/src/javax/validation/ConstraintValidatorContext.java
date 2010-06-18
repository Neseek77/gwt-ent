package javax.validation;

/**
 * Provide contextual data and operation when applying a given constraint
 * validator
 * 
 * @author Emmanuel Bernard
 */
public interface ConstraintValidatorContext {
	/**
	 * Disable the default error message and default ConstraintViolation object
	 * generation. Useful to set a different error message or generate a
	 * ConstraintViolation based on a different property
	 * 
	 * @see #addError(String)
	 * @see #addError(String, String)
	 */
	void disableDefaultError();

	/**
	 * @return the current uninterpolated default message
	 */
	String getDefaultErrorMessage();

	/**
	 * Add a new error message. This error message will be interpolated.
	 * <p/>
	 * If <code>isValid<code> returns <code>false</code>, a
	 * <code>ConstraintViolation</code> object will * per error message including
	 * the default one unless {@link #disableDefaultError()} has been called.
	 * <p/>
	 * Aside from the error message, <code>ConstraintViolation</code> objects
	 * generated from such a call contain the same contextual information (root
	 * bean, path and so on)
	 * <p/>
	 * This method can be called multiple times. One
	 * <code>ConstraintViolation</code> instance per call is created.
	 * 
	 * @param message
	 *          new uninterpolated error message.
	 */
	void addError(String message);

	/**
	 * Add a new error message to a given sub property <code>property</code>. The
	 * subproperty is relative to the path tot he bean or property hosting the
	 * constraint.
	 * 
	 * This error message will be interpolated.
	 * <p/>
	 * If <code>isValid</code> returns <code>false</code>, a
	 * <code>ConstraintViolation</code> object will * per error message including
	 * the default one unless {@link #disableDefaultError()} has been called.
	 * <p/>
	 * Aside from the error message and the property path,
	 * <code>ConstraintViolation</code> objects generated from such a call contain
	 * the same contextual information (root bean, leaf bean etc)
	 * <p/>
	 * This method can be called multiple times. One
	 * <code>ConstraintViolation</code> instance per call is created.
	 * 
	 * @param message
	 *          new uninterpolated error message.
	 * @param property
	 *          property name the </code>ConstraintViolation</code> is targeting.
	 */
	void addError(String message, String property);
}
