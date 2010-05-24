package com.gwtent.client.test.template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gwtent.htmltemplate.client.HTMLTemplate;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@HTMLTemplate(basePath="/com/gwtent/client/test/template/")
public @interface HTMLTemplateTest {
	String value() default "";
}
