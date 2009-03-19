package javax.validation;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 */
@Documented
@ConstraintValidator(LengthConstraint.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Length {
    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "{beancheck.length}";

    String[] groups() default {};
}