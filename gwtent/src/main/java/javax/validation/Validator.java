package javax.validation;

import java.util.Set;

/**
 * Validate bean instances. Implementations of this interface must be
 * thread-safe.
 * 
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface Validator {
	/**
	 * Validates all constraints on object.
	 * 
	 * @param object
	 *          object to validate
	 * @param groups
	 *          groups targeted for validation (default to
	 *          {@link javax.validation.groups.Default})
	 * 
	 * @return constraint violations or an empty Set if none
	 * 
	 * @throws IllegalArgumentException
	 *           if object is null
	 * @throws ValidationException
	 *           if a non recoverable error happens during the validation process
	 */
	<T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups);

	/**
	 * Validates all constraints placed on the property named
	 * &lt;code&gt;propertyName&lt;/code&gt; of <code>object</code>
	 * 
	 * @param object
	 *          object to validate
	 * @param propertyName
	 *          property to validate (ie field and getter constraints)
	 * @param groups
	 *          groups targeted for validation (default to
	 *          {@link javax.validation.groups.Default})
	 * 
	 * @return constraint violations or an empty Set if none
	 * 
	 * @throws IllegalArgumentException
	 *           if object is null, if propertyName null, empty or not a valid
	 *           object property
	 * @throws ValidationException
	 *           if a non recoverable error happens during the validation process
	 */
	<T> Set<ConstraintViolation<T>> validateProperty(T object,
			String propertyName, Class<?>... groups);

	/**
	 * Validates all constraints placed on the property named
	 * <code>propertyName</code> would the property value be <code>value</code>
	 * <p/>
	 * <code>ConstraintViolation</code> objects return null for
	 * {@link ConstraintViolation#getRootBean()} and
	 * {@link ConstraintViolation#getLeafBean()}
	 * 
	 * @param beanType
	 *          the bean type
	 * @param propertyName
	 *          property to validate
	 * @param value
	 *          property value to validate
	 * @param groups
	 *          groups targeted for validation (default to
	 *          {@link javax.validation.groups.Default})
	 * 
	 * @return constraint violations or an empty Set if none
	 * 
	 * @throws IllegalArgumentException
	 *           if object is null, if propertyName null, empty or not a valid
	 *           object property
	 * @throws ValidationException
	 *           if a non recoverable error happens during the validation process
	 */
	<T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,
			String propertyName, Object value, Class<?>... groups);

	/**
	 * Return the descriptor object describing bean constraints The returned
	 * object (and associated objects including ConstraintDescriptors) are
	 * immutable.
	 * 
	 * @param clazz
	 *          class type evaluated
	 * 
	 * @return the bean descriptor for the specified class.
	 * 
	 * @throws ValidationException
	 *           if a non recoverable error happens during the metadata discovery
	 *           or if some constraints are invalid.
	 */
	//BeanDescriptor getConstraintsForClass(Class<?> clazz);
}