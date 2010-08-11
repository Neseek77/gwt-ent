package com.gwtent.validate.client.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

import javax.validation.Constraint;

import com.gwtent.validate.client.constraints.impl.EmailValidator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * <pre>
 * --
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --
 * </pre>
 **/
@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { METHOD, FIELD })
@Retention(RUNTIME)
public @interface Email {
	Class<?>[] groups() default {};

	String message() default "{constraint_email}";
}
