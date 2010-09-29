package com.gwtent.validate.client.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Constructor;
import com.gwtent.reflection.client.TypeOracle;

/**
 * 
 * @author James Luo
 *
 * 29/07/2010 5:18:21 PM
 */
public class ConstraintValidatorFactoryImpl implements
		ConstraintValidatorFactory {

  private static ConstraintValidatorFactory instance = new ConstraintValidatorFactoryImpl();
  
  public static ConstraintValidatorFactory getInstance(){
    return instance;
  }
  

	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		ClassType type = TypeOracle.Util.getInstance().getClassType(key);
    
    Constructor constructor = type.findConstructor(new String[]{});
    return (T) constructor.newInstance();
	}
}
