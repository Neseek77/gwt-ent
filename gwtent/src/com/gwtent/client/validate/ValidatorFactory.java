package com.gwtent.client.validate;

import javax.validation.Validator;

public interface ValidatorFactory {

  /**
   * Get validator for a class
   * @param <T>
   * @param clazz the object class to validate
   * @return the Validator of this class
   */
  public abstract <T extends Object> Validator<T> getValidator(Class<T> clazz);

}