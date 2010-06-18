package javax.validation;

/**
 * Describe a constraint violation. This object describe the error context as
 * well as the message describing the violation.
 * 
 * @author Emmanuel Bernard
 */
public interface ConstraintViolation<T> {
	/**
	 * @return The interpolated error message for this constraint violation.
	 */
	String getMessage();

	/**
	 * @return The non-interpolated error message for this constraint violation.
	 */
	String getMessageTemplate();

	/**
	 * @return The root bean being validated. Null when returned by
	 *         {@link javax.validation.Validator#validateValue(Class, String, Object, Class[])}
	 */
	T getRootBean();

	/**
	 * If a bean constraint, the bean instance the constraint is applied on If a
	 * property constraint, the bean instance hosting the property the constraint
	 * is applied on
	 * 
	 * @return the leaf bean the constraint is applied on. Null when returned by
	 *         {@link javax.validation.Validator#validateValue(Class, String, Object, Class[])}
	 */
	Object getLeafBean();

	/**
	 * @return the property path to the value from <code>rootBean</code>
	 *         <code>null</code> if the value is the <code>rootBean<code> itself.
	 */
	String getPropertyPath();

	/**
	 * @return the value failing to pass the constraint.
	 */
	Object getInvalidValue();

	/**
	 * Constraint metadata reported to fail. The returned instance is immutable.
	 * 
	 * @return constraint metadata
	 */
	ConstraintDescriptor<?> getConstraintDescriptor();
}