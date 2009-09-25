package com.gwtent.client.test.reflection;

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
}
