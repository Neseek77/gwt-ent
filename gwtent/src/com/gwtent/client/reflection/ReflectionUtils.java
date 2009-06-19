package com.gwtent.client.reflection;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtils {
  
	public static String[] getGetterNames(String name){
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		String[] result = {"get" + name,
				"is" + name};
		return result;
	}
	
	public static String[] getSetterNames(String name){
		String[] result = {"set" + name.substring(0, 1).toUpperCase() + name.substring(1)};
		return result;
	}
	
	/**
	 * Guess setter method of a field name
	 * if get more then 1 method, this function will raise an error
	 * if you have the value to set into a field, please using 
	 * getSetter(ClassType, fieldName, ObjectVlaue)
	 * 
	 * @param classType
	 * @param fieldName
	 * @return
	 */
	public static Method getSetter(ClassType classType, String fieldName){
		for (String methodName : getSetterNames(fieldName)){
			List<Method> methods = new ArrayList<Method>();
      for (Method method : classType.getMethods()){
        if ((method.getName().equals(methodName)) && (method.getParameters().length == 1)){
          methods.add(method);
        }
      }
      
      if (methods.size() == 1)
        return methods.get(0);
      else{
      	if (methods.size() > 1)
        	throw new RuntimeException("Found more then one setter of " + fieldName + " in class " + classType.getName());
      }
		}
		
		if (classType.getSuperclass() != null)
			return getSetter(classType.getSuperclass(), fieldName);
		
		return null;
	}
	
	/**
	 * If you have value to set into field
	 * please using this function, this function will check more and 
	 * found the right method of the value
	 * 
	 * @param classType
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static Method getSetter(ClassType classType, String fieldName, Object value){
		String typeName = value.getClass().getName();
		for (String methodName : getSetterNames(fieldName)){
			Method method = classType.findMethod(methodName, new String[]{typeName});
			
			if (method != null)
				return method;
		}
		
		return getSetter(classType, fieldName);
	}
	
	public static Method getGetter(ClassType classType, String fieldName){
		for (String methodName : getGetterNames(fieldName)){
			Method method = classType.findMethod(methodName);
			if (method != null)
				return method;
		}
		
		return null;
	}
	
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
    throw new ReflectionRequiredException("your class should have reflection information before this opeartion. This can be done by annotated class with \"@Reflectionable\" annotations, ie: \"@Reflectable\", \"@Validtable\", \"@DataContract\" or implement flag interface \"Reflection\". Current class is : " + className + "\n" + msg);
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
  	if (type == null)
  		reflectionRequired(annotation.annotationType().getName(), "");
  	
  	Method method = type.findMethod(methodName);
  	return method.invoke(annotation);
  }
  
  
  public static Map<String, Object> getAnnotationValues(Annotation annotation){
  	ClassType type = TypeOracle.Instance.getClassType(annotation.annotationType());
  	if (type == null)
  		reflectionRequired(annotation.annotationType().getName(), "");
  	
  	Map<String, Object> result = new HashMap<String, Object>();
  	
  	Method[] methods = type.getMethods();
  	for (Method method : methods){
  		result.put(method.getName(), getAnnotationValueByName(annotation, method.getName()));
  	}
  	
  	return result;
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
  
  public static Method findMethodByName(ClassType classType, String methodName){
  	ClassType parent = classType;
	  while (parent != null){
	    for (Method method : parent.getMethods()){
	    	if (method.getName().equals(methodName))
	    		return method;
	    }
	    
	    parent = parent.getSuperclass();
	  }
	  
	  return null;
  }
  
  /**
   * return true if "classToTest" is assignable to "parentClass"
   * @param parentClass
   * @param classToTest
   * @return
   */
  public static boolean isAssignable(Class<?> parentClass, Class<?> classToTest){
  	checkReflection(classToTest);
  	
  	ClassType typeToTest = TypeOracle.Instance.getClassType(classToTest);
  	
  	if (testAssignableWithoutSuper(parentClass, typeToTest))
  		return true;
  	
  	for (ClassType type : typeToTest.getImplementedInterfaces()){
			if (isAssignable(parentClass, type.getDeclaringClass()))
				return true;
		}
  	
  	ClassType parentToTest = typeToTest.getSuperclass();
  	while (parentToTest != null){
  		if (isAssignable(parentClass, parentToTest.getDeclaringClass()))
  			return true;
  		
  		parentToTest = parentToTest.getSuperclass();
  	}
  	
  	return false;
  }
  
  private static boolean testAssignableWithoutSuper(Class<?> parentClass, ClassType typeToTest){
  	if (typeToTest.getDeclaringClass() == parentClass)
  		return true;
  	else
  		return false;
  }
}
