package javax.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 **/
@Documented
@ConstraintValidator(NotEmptyConstraint.class)
@Target({METHOD,FIELD})
@Retention(RUNTIME)
public @interface NotEmpty {
    String[] groups() default {};
    String message() default "{beancheck.notEmpty}";
}
