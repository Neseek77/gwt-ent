package com.gwtent.client.test.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

public class TestAnnotationInAnnotation {
	public static @interface AnnotationHolder{
		TestAnnotation[] annotations();
	}
	
	public static @interface TestAnnotation{
		String name();
		String value();
	}
	
	@AnnotationHolder(annotations={@TestAnnotation(name = "anno1", value = "anno1-value"), @TestAnnotation(name = "anno2", value = "anno2-value")})
	public static class TestIt{
		
	}
	
	
	@Target(ElementType.ANNOTATION_TYPE)
	public static @interface MyParameterAnn {
	  int p1();
	  int p2() default 0;
	}
	
	
	@Target(ElementType.METHOD)
	@Inherited
	public @interface MyMethodAnn {
	  int p1();
	  MyParameterAnn pa() default @MyParameterAnn(p1 = 0);

	} 

}
