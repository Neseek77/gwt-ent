package com.gwtent.client.reflection;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {
  
  public static class ReflectionRequiredException extends RuntimeException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public ReflectionRequiredException(){
      super();
    }
    
    public ReflectionRequiredException(String message){
      super(message);
    }
  } 
  
  public static void reflectionRequired(String className, String msg){
    throw new ReflectionRequiredException("your class should have reflection information before this opeartion. This can be done by annotated class with \"@Reflectionable\" annotations, ie: \"@Reflectionable\", \"@Validtable\", \"@DataContract\" or implement flag interface \"Reflection\". Current class is : " + className + "\n" + msg);
  }
  
  /**
   * clazz must have reflection information before continue
   * 
   * @param clazz
   */
  public static void reflectionRequired(Class<?> clazz){
    reflectionRequired(clazz.getName(), null);
  }
  
  public static void reflectionRequired(Class<?> clazz, String msg){
    reflectionRequired(clazz.getName(), msg);
  }
  
  public static boolean checkReflection(String className){
    boolean result = TypeOracle.Instance.getClassType(className) != null;
    
    if (! result)
      ReflectionUtils.reflectionRequired(className, "");
    
    return result;
  }

  /**
   * Check clazz to see if it have reflection information
   * if not, raise a ReflectionRequiredException
   * @param clazz
   */
  public static void checkReflection(Class<?> clazz){
    boolean result = TypeOracle.Instance.getClassType(clazz) != null;
    
    if (! result)
      ReflectionUtils.reflectionRequired(clazz.getName(), "");
  }
  
  /**
   * Get value from annotation which named "methodName"
   * i.e: Annotation(value="abc")
   * 
   * @param annotation annotation
   * @param methodName "value"
   * @return "abc"
   */
  public static Object getAnnotationValueByName(Annotation annotation, String methodName){
  	ClassType type = TypeOracle.Instance.getClassType(annotation.annotationType());
  	Method method = type.findMethod(methodName);
  	return method.invoke(annotation);
  }
  
  
	/**
	 * Find annotation from array of annotations
	 * @param annos the array of annotations
	 * @param clazz the class of annotation
	 * @return the annotation which meet clazz
	 */
  public static <T extends Annotation> T getAnnotation(Annotation[] annos, Class<T>clazz){
    ClassType classType = TypeOracle.Instance.getClassType(clazz);
    for (Annotation anno : annos){
      if (anno.annotationType().getName() == classType.getName())
        return (T) anno;
    }
    
    return null;
  }
  
  /**
   * Get meta annotation from an annotation
   * @param store the annotation which annotated by meta annotation
   * @param clazz the meta annotation
   * @return if store has annotated by meta annotation, return that annotation, otherwise, return null
   */
  public static <T extends Annotation>T getMetaAnnotation(Annotation store, Class<T> clazz) {
    ClassType annoClass = TypeOracle.Instance.getClassType(store.annotationType());
    if (annoClass != null){
      return ReflectionUtils.getAnnotation(annoClass.getAnnotations(), clazz);
    }
    return null;
  }
  
  /**
   * Get all fields which annotated by clazz.
   * @param classType the classType which contains fields
   * @param clazz the annotation class
   * @return all fields which annotated by clazz
   */
  public static Field[] getAllFields(ClassType classType, Class<? extends Annotation> clazz){
  	List<Field> fields = new ArrayList<Field>();
  	for (Field field : classType.getFields()){
  		Annotation annotation = getAnnotation(field.getAnnotations(), clazz);
  		if (annotation != null)
  			fields.add(field);
  	}
  	return fields.toArray(new Field[0]);
  }
  
  /**
   * Get all methods which annotated by clazz
   * @param classType
   * @param clazz
   * @return
   */
  public static Method[] getAllMethods(ClassType classType, Class<? extends Annotation> clazz){
  	List<Method> methods = new ArrayList<Method>();
  	for (Method method : classType.getMethods()){
  		Annotation annotation = getAnnotation(method.getAnnotations(), clazz);
  		if (annotation != null)
  			methods.add(method);
  	}
  	return methods.toArray(new Method[0]);
  }
}
