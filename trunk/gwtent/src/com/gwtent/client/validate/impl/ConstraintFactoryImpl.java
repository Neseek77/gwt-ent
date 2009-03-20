package com.gwtent.client.validate.impl;

import javax.validation.Constraint;
import javax.validation.ConstraintFactory;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Constructor;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.client.validate.ValidateUtils;

public class ConstraintFactoryImpl implements ConstraintFactory {

  private static ConstraintFactoryImpl instance = new ConstraintFactoryImpl();
  
  public static ConstraintFactoryImpl getInstance(){
    return instance;
  }
  
  @SuppressWarnings("unchecked")
  public <T extends Constraint> T getInstance(Class<T> constraintClass) {
    ClassType type = TypeOracle.Instance.getClassType(constraintClass);
    
    Constructor constructor = type.findConstructor(new String[]{});
    return (T) constructor.newInstance();
  }

}
