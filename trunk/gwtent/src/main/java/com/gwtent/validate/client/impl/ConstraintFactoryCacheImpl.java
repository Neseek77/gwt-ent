package com.gwtent.validate.client.impl;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

public class ConstraintFactoryCacheImpl implements ConstraintValidatorFactory {

  private static ConstraintFactoryCacheImpl instance = new ConstraintFactoryCacheImpl();
  public static ConstraintFactoryCacheImpl getInstance(){
    return instance;
  }
  
  private Map<Class<?>, ConstraintValidator<?, ?>> map = new HashMap<Class<?>, ConstraintValidator<?, ?>>(); 
  
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		T result = (T) map.get(key);
    if (result == null){
      result = ConstraintValidatorFactoryImpl.getInstance().getInstance(key);
      map.put(key, result);
    }
    
    return result;
	}

}
