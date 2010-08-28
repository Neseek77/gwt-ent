package com.gwtent.client.test.validate.tck;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Documented
public @interface SpecAssertion {
	public String  section();
	public String  id() default "";
	public String  note() default "";
}