package com.gwtent.client.reflection;

import java.lang.annotation.Annotation;

public class ReflectionUtils {
  
  public static AnnotationStore getAnnotation(AnnotationStore[] annos, Class<? extends Annotation>clazz){
    ClassType classType = TypeOracle.Instance.getClassType(clazz);
    for (AnnotationStore anno : annos){
      if (anno.annotationType().getName() == classType.getName())
        return anno;
    }
    
    return null;
  }
  
  public static AnnotationStore getMetaAnnotation(AnnotationStore store, Class<? extends Annotation> clazz) {
    ClassType annoClass = TypeOracle.Instance.getClassType(store.annotationType());
    if (annoClass != null){
      return ReflectionUtils.getAnnotation(annoClass.getAnnotations(), clazz);
    }
    return null;
  }
}
