package com.gwtent.validate.client.impl;

import javax.validation.ConstraintDescriptor;
import javax.validation.ConstraintViolation;

public class ConstraintViolationImpl<T> implements ConstraintViolation<T> {

  private final Class<?> beanClass;
  private final String message;
  private final String messageTemplate;
  private final String propertyPath;
  private final T rootBean;
  private final Object value;
  
  public ConstraintViolationImpl(Class<?> beanClass, String message, String messageTemplate, String propertyPath, T rootBean, Object value){
    this.beanClass = beanClass;
    this.message = message;
    this.messageTemplate = messageTemplate;
    this.propertyPath = propertyPath;
    this.rootBean = rootBean;
    this.value = value;
  }
  
  public Class<?> getBeanClass() {
    return this.beanClass;
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

	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return null;
	}

	public Object getInvalidValue() {
		return value;
	}

	public Object getLeafBean() {
		return null;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(message).append(":");
		
		if (beanClass != null)
			sb.append(beanClass.getName());
		
		sb.append(" - ").append(propertyPath);
		
		sb.append(" Value:").append(value);
		
		return sb.toString();
	}

}
