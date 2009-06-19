package com.gwtent.client.validate;

import javax.validation.Validator;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;

public class ValidatorFactoryDefault implements ClientValidatorFactory{

  public Validator getValidator(ClassType classType) {
    // TODO Auto-generated method stub
    return null;
  }

  public Validator getValidator(Class<?> clazz) {
    ReflectionUtils.checkReflection(clazz);
    
    ClassType classType = TypeOracle.Instance.getClassType(clazz);
    return getValidator(classType);
  }

}
