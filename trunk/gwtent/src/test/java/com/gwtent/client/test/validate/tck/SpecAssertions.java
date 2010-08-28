package com.gwtent.client.test.validate.tck;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * 
 * @author James Luo
 *
 * 27/08/2010 4:45:36 PM
 */
@Target(ElementType.METHOD)
@Documented
public @interface SpecAssertions  {
	SpecAssertion[] value();
}