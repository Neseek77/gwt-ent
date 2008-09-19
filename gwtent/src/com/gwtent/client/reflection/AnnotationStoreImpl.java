/**
 * FileName: AnnotationStoreImpl.java
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.client.reflection;

import java.lang.annotation.Annotation;
import java.util.Map;

public class AnnotationStoreImpl implements AnnotationStore {

  private final Class<? extends Annotation> clasz;
  private final Map<String, Object> values;
  
  public AnnotationStoreImpl(Class<? extends Annotation> clasz, Map<String, Object> values){
    this.clasz = clasz;
    this.values = values;
  }
  
  public Class<? extends Annotation> annotationType() {
    return clasz;
  }

  public Object getValue(String name) {
    return values.get(name);
  }

}
