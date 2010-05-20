package com.gwtent.validate.client.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Constructor;
import com.gwtent.reflection.client.TypeOracle;

public class ConstraintFactoryImpl implements ConstraintValidatorFactory {

  private static ConstraintFactoryImpl instance = new ConstraintFactoryImpl();
  
  public static ConstraintFactoryImpl getInstance(){
    return instance;
  }
  

	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		ClassType type = TypeOracle.Instance.getClassType(key);
    
    Constructor constructor = type.findConstructor(new String[]{});
    return (T) constructor.newInstance();
	}

}
