package javax.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import java.io.Serializable;

import javax.validation.Constraint;

/**
 * <pre>
 * --
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --
 * </pre>
 */
@Documented
@Constraint(validatedBy = MinValidator.class)
@Target( { ElementType.METHOD, FIELD })
@Retention(RUNTIME)
public @interface Min {
	Class<?>[] groups() default {};

	int value();

	String message() default "{constraint_min}";
}