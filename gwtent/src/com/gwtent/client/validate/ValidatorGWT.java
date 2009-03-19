package com.gwtent.client.validate;

import java.util.Set;

import javax.validation.ElementDescriptor;
import javax.validation.InvalidConstraint;
import javax.validation.MessageResolver;
import javax.validation.Validator;

public class ValidatorGWT<T> implements Validator<T>{

  private void notSupportYet(){
    throw new RuntimeException("No support yet.");
  }
  
  public ElementDescriptor getBeanConstraints() {
    notSupportYet();
    return null;
  }

  public ElementDescriptor getConstraintsForProperty(String propertyName) {
    notSupportYet();
    return null;
  }

  public Set<String> getValidatedProperties() {
    notSupportYet();
    return null;
  }

  public boolean hasConstraints() {
    notSupportYet();
    return false;
  }

  public void setMessageResolver(MessageResolver messageResolver) {
    notSupportYet();
    
  }

  public Set<InvalidConstraint<T>> validate(T object, String... groups) {
    notSupportYet();
    return null;
  }

  public Set<InvalidConstraint<T>> validateProperty(T object,
      String propertyName, String... groups) {
    notSupportYet();
    return null;
  }

  public Set<InvalidConstraint<T>> validateValue(String propertyName,
      Object value, String... groups) {
    notSupportYet();
    return null;
  }

}
