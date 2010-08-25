package com.gwtent.validate.client.constraints;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * The annotated element has to be in the appropriate range. Apply on numeric values or string
 * representation of the numeric value.
 *
 * @author Hardy Ferentschik
 */
@Documented
@Constraint(validatedBy = { })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Min(0)
@Max(Long.MAX_VALUE)
@ReportAsSingleViolation
public @interface Range {
	@OverridesAttribute(constraint = Min.class, name = "value")
	long min() default 0;

	@OverridesAttribute(constraint = Max.class, name = "value")
	long max() default Long.MAX_VALUE;

	String message() default "{com.gwtent.validate.client.constraints.Range.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}