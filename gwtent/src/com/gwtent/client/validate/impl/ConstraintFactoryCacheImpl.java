package com.gwtent.client.validate.impl;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Constraint;
import javax.validation.ConstraintFactory;

public class ConstraintFactoryCacheImpl implements ConstraintFactory {

  private static ConstraintFactoryCacheImpl instance = new ConstraintFactoryCacheImpl();
  public static ConstraintFactoryCacheImpl getInstance(){
    return instance;
  }
  
  private Map<Class<?>, Constraint> map = new HashMap<Class<?>, Constraint>(); 
  
  public <T extends Constraint> T getInstance(Class<T> constraintClass) {
    T result = (T) map.get(constraintClass);
    if (result == null){
      result = ConstraintFactoryImpl.getInstance().getInstance(constraintClass);
      map.put(constraintClass, result);
    }
    
    return result;
  }

}
