package com.gwtent.validate.client.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

import javax.validation.Constraint;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Documented
@Constraint(validatedBy = URLValidator.class)
@Target( { METHOD, FIELD })
@Retention(RUNTIME)
public @interface URL {
	Class<?>[] groups() default {};

	String message() default "{constraint_url}";
}
