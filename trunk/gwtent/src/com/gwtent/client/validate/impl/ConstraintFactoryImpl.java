package com.gwtent.client.validate.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Constructor;
import com.gwtent.client.reflection.TypeOracle;

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
