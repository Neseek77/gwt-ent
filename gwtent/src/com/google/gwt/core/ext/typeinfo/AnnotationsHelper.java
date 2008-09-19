/**
 * FileName: JRealClassTypeHelper.java
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.google.gwt.core.ext.typeinfo;

import java.lang.annotation.Annotation;

public class AnnotationsHelper {
  public static Annotation[] getAnnotations(JRealClassType classType){
    return classType.getAnnotations();
  }
  
  public static Annotation[] getAnnotations(JAbstractMethod type){
    return type.getAnnotations();
  }
  
  public static Annotation[] getAnnotations(JField type){
    return type.getAnnotations();
  }
  
  public static Annotation[] getAnnotations(JPackage type){
    return type.getAnnotations();
  }
  
  public static Annotation[] getAnnotations(JParameter type){
    return type.getAnnotations();
  }
  
}
