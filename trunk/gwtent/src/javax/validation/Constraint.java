package javax.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gwtent.client.reflection.annotations.Reflect_Full;

/**
* Link between a constraint annotation and its constraint validation implementations.
* <p/>
* A given constraint annotation should be annotated by a @Constraint
* annotation which refers to its list of constraint validation implementations.
*
* @author Emmanuel Bernard
* @author Gavin King
* @author Hardy Ferentschik
*/
@Documented
@Target({ ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {
	/**
	* ConstraintValidator classes must reference distinct target types.
	* If two validators refer to the same type, an exception will occur.
	*
	* @return array of ConstraintValidator classes implementing the constraint
	*/
	public Class<? extends ConstraintValidator<?,?>>[] validatedBy();
}