package javax.validation;

import java.util.Locale;

/**
 * Interpolate a given constraint error message. Implementations should be as
 * tolerant as possible on syntax errors.
 * 
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface MessageInterpolator {
	/**
	 * Interpolate the message from the constraint parameters and the actual
	 * validated object. The locale is defaulted according to the
	 * <code>MessageInterpolator</code> implementation. See the implementation
	 * documentation for more detail.
	 * 
	 * @param messageTemplate
	 *          The message to interpolate.
	 * @param context
	 *          contextual information related to the interpolation
	 * 
	 * @return Interpolated error message.
	 */
	String interpolate(String messageTemplate, Context context);

	/**
	 * Interpolate the message from the constraint parameters and the actual
	 * validated object. The Locale used is provided as a parameter.
	 * 
	 * @param messageTemplate
	 *          The message to interpolate.
	 * @param context
	 *          contextual information related to the interpolation
	 * @param locale
	 *          the locale targeted for the message
	 * 
	 * @return Interpolated error message.
	 * 
	 * JamesLuo.au@gmail.com
	 * GWT message interpolator not support Locale parameter, 
	 * please using \"public String interpolate(String messageTemplate, Context context)\", the Locale is the same with you current GWT locale.
	 */
	//String interpolate(String messageTemplate, Context context, Locale locale);

	/**
	 * Informations related to the interpolation context
	 */
	static interface Context {
		/**
		 * @return ConstraintDescriptor corresponding to the constraint being
		 *         validated
		 */
		ConstraintDescriptor<?> getConstraintDescriptor();

		/**
		 * @return value being validated
		 */
		Object getValidatedValue();

	}
}