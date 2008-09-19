/**
 * FileName: Annotation.java
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.client.reflection;

import java.lang.annotation.Annotation;

public interface AnnotationStore {
  Class<? extends Annotation> annotationType();
  
  public Object getValue(String name);
  
}
