package javax.validation.constraints;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

import javax.validation.Constraint;


@Documented
@Constraint(validatedBy=SizeValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Size {
    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "{constraint_size}";

    Class<?>[] groups() default {};
}