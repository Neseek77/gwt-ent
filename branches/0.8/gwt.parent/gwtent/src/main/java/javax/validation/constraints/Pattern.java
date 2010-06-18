package javax.validation.constraints;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Documented
@Constraint(validatedBy = PatternValidator.class)
@Target( { METHOD, FIELD })
@Retention(RUNTIME)
public @interface Pattern {
	/** regular expression */
	String regex();

	/** Flags parameter for Pattern.compile() */
	int flags() default 0;

	String message() default "{constraint_pattern}";

	Class<?>[] groups() default {};
}
