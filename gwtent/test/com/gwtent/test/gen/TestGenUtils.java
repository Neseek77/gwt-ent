package com.gwtent.test.gen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.annotation.Aspect;

import com.gwtent.client.reflection.Reflectionable;
import com.gwtent.gen.GenUtils;

import junit.framework.TestCase;

public class TestGenUtils extends TestCase {

  @Reflectionable
  public class A {
    
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @Reflectionable
  public @interface MyReflectionable {

  }
  
  @MyReflectionable
  @Aspect
  public class B{
    
  }
  
  public void testGetClassTypeAnnotationWithMataAnnotation(){
    Reflectionable result = GenUtils.getAnnotationFromAnnotation(B.class.getAnnotation(MyReflectionable.class), Reflectionable.class);
    
    assertTrue(result != null);
    
    result = GenUtils.getAnnotationFromAnnotation(B.class.getAnnotation(Aspect.class), Reflectionable.class);
    assertTrue(result == null);
  }
  
}
