package com.gwtent.client.validate;

import com.gwtent.client.reflection.TypeOracle;

public class ValidateUtils {
  public static boolean checkReflection(String className){
    boolean result = TypeOracle.Instance.getClassType(className) != null;
    
    if (! result)
      reflectionRequired(className, "");
    
    return result;
  }
  
  public static boolean checkReflection(Class<?> clazz){
    boolean result = TypeOracle.Instance.getClassType(clazz) != null;
    
    if (! result)
      reflectionRequired(clazz.getName(), "");
    
    return result;
  }
  
  public static void reflectionRequired(String className, String msg){
    throw new RuntimeException("your class should have reflection information before validte. This can be done by annotation class with \"@Validtable\" or \"@Reflectionable\". Current class is : " + className + "\n" + msg);
  }
  
  public static void reflectionRequired(Class<?> clazz){
    reflectionRequired(clazz.getName(), null);
  }
  
  public static void reflectionRequired(Class<?> clazz, String msg){
    reflectionRequired(clazz.getName(), msg);
  }
}
