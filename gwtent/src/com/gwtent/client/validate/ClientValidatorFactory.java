package com.gwtent.client.validate;

import javax.validation.Validator;

import com.gwtent.client.reflection.ClassType;

public interface ClientValidatorFactory extends ValidatorFactory {

  /**
   * Get validator for a class
   * @param <T>
   * @param classType the object ClassType to validate
   * @return The Validator fo this class
   */
  public <T extends Object> Validator<T> getValidator(ClassType classType);
  
}
