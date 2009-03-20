package com.gwtent.client.validate.impl;

import javax.validation.InvalidConstraint;

public class InvalidConstraintImpl<T> implements InvalidConstraint<T> {

  private final Class beanClass;
  private final String[] groups;
  private final String message;
  private final String propertyPath;
  private final T rootBean;
  private final Object value;
  
  public InvalidConstraintImpl(Class beanClass, String[] groups,
      String message, String propertyPath, T rootBean, Object value){
    this.beanClass = beanClass;
    this.groups = groups;
    this.message = message;
    this.propertyPath = propertyPath;
    this.rootBean = rootBean;
    this.value = value;
  }
  
  public Class getBeanClass() {
    return this.beanClass;
  }

  public String[] getGroups() {
    return this.groups;
  }

  public String getMessage() {
    return this.message;
  }

  public String getPropertyPath() {
    return this.propertyPath;
  }

  public T getRootBean() {
    return this.rootBean;
  }

  public Object getValue() {
    return this.value;
  }

}
