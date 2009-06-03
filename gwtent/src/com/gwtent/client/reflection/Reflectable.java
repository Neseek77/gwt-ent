package com.gwtent.client.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Reflectable {
  
  /**
   * not finish yet.
   * 
   * If this assigned, all the class which assignable with 
   * "assignableClasses" will make its reflection enabled
   * 
   * @return the classes you want generate reflection information
   */
  public Class<?>[] assignableClasses() default {};
}
