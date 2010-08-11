package javax.validation;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Describes a single constraint and its composing constraints. T is the
 * constraint's annotation type.
 * 
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface ConstraintDescriptor<T extends Annotation> {
	/**
	 * Returns the annotation describing the constraint declaration. If a
	 * composing constraint, attribute values are reflecting the overridden
	 * attributes from the composing constraint
	 * 
	 * @return The annotation for this constraint.
	 */
	T getAnnotation();

	/**
	 * The Set of groups the constraint is applied on. If the constraint declares
	 * no group, the <code>Default</code> group is returned.
	 * 
	 * @return The groups the constraint is applied on.
	 */
	Set<Class<?>> getGroups();

	/**
	 * Immutable list of the constraint validation implementation classes.
	 * 
	 * @return list of the constraint validation implementation classes.
	 */
	List<Class<? extends ConstraintValidator<T, ?>>> getConstraintValidatorClasses();

	/**
	 * Returns a map containing the annotation attribute names as keys and the
	 * annotation attribute values as value. If this constraint is used as part of
	 * a composed constraint, attribute values are reflecting the overridden
	 * attribute from the composing constraint.
	 * 
	 * @return a map containing the annotation attribute names as keys and the
	 *         annotation attribute values as value. Constraint metadata request
	 *         APIs 1.0.CR1 Proposed Final Draft 81
	 */
	Map<String, Object> getAttributes();

	/**
	 * Return a set of composing <code>ConstraintDescriptor</code>s where each
	 * descriptor describes a composing constraint.
	 * <code>ConstraintDescriptor</code> instances of composing constraints
	 * reflect overridden attribute values in {@link #getAttributes()} and
	 * {@link #getAnnotation()}.
	 * 
	 * @return a set of <code>ConstraintDescriptor<code> objects or an empty set
* in case there are no composing constraints.
	 */
	Set<ConstraintDescriptor<?>> getComposingConstraints();

	/**
	 * @return true if the constraint is annotated with @ReportAsSingleViolation
	 */
	boolean isReportAsSingleViolation();
}