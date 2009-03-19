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
    ClassType classType = TypeOracle.Instance.getClassType(clazz);
    
    if (classType != null)
      return getValidator(classType);
    else
      throw new RuntimeException("your class should have reflection information before validte. This can be done by annotation class with \"@Validtable\" or \"@Reflectionable\". Current class is : " + clazz.getName());
  }

}
