package javax.validation;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.<br>
 *
 * The early draft specification sometimes calls this annotation ValidatorClass.
 * This might be changed in the final specification.
 * --</pre>
 *
 * Link between an constraint annotation and its constraint validation implementation
 * <p/>
 * A given constraint annotation should be annotated by a @ConstraintValidator
 * annotation to refer to its constraint validation implementation
 */
@Documented
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ConstraintValidator {
    /** Constraint validation implementation */
    Class<? extends Constraint> value();
}
