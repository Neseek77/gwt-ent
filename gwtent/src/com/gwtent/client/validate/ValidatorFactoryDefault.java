package com.gwtent.client.validate;

import javax.validation.Validator;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.TypeOracle;

public class ValidatorFactoryDefault implements ClientValidatorFactory{

  public <T> Validator<T> getValidator(ClassType classType) {
    // TODO Auto-generated method stub
    return null;
  }

  public <T> Validator<T> getValidator(Class<T> clazz) {
    if (ValidateUtils.checkReflection(clazz)){
      ClassType classType = TypeOracle.Instance.getClassType(clazz);
      return getValidator(classType);
    }
    
    return null;
  }

}
