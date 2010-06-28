package com.gwtent.showcase.client.validate;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Documented
@Constraint(validatedBy = MyDOBValidator.class)
@Target( { METHOD, FIELD })
@Retention(RUNTIME)
public @interface MyDOB {
	Class<?>[] groups() default {};

	String message() default "you need to be 13 years old.";
}
