package com.gwtent.client.validate.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ElementDescriptor;
import javax.validation.InvalidConstraint;
import javax.validation.MessageResolver;
import javax.validation.Validator;

import com.gwtent.client.reflection.AnnotationStore;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.HasAnnotations;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.client.validate.ValidateUtils;

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
    ClassType type = TypeOracle.Instance.getClassType(object.getClass());
    if (type == null)
      ValidateUtils.reflectionRequired(object.getClass());
    
    Set<InvalidConstraint<T>> icSet = new HashSet<InvalidConstraint<T>>();
    
    for (Field field : type.getFields()){
      icSet.addAll(validateProperty(object, field.getName(), groups));
    }
    
    for (Method method : type.getMethods()){
      icSet.addAll(validateProperty(object, method.getName(), groups));
    }
    
    return icSet;
  }

  public Set<InvalidConstraint<T>> validateProperty(T object,
      String propertyName, String... groups) {
    List<String> lstGroups = new ArrayList<String>();
    for (String str : groups){
      lstGroups.add(str);
    }
    if (lstGroups.size() <= 0)
      lstGroups.add("default");
    
    ClassType type = TypeOracle.Instance.getClassType(object.getClass());
    if (type == null)
      ValidateUtils.reflectionRequired(object.getClass());
    
    Set<InvalidConstraint<T>> icSet = new HashSet<InvalidConstraint<T>>();
    
    Field field = type.getField(propertyName);
    if (field != null){
      doValidate(object, propertyName, type, icSet, field, "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
    }else{
      Method method = type.getMethod(propertyName, null);
      if (method != null)
        doValidate(object, propertyName, type, icSet, method, propertyName);
    }
    
    return icSet;
  }

  private void doValidate(T object, String propertyName, ClassType type,
      Set<InvalidConstraint<T>> icSet, HasAnnotations field, String getterName) {
    AnnotationStore[] annotations = field.getAnnotations();
    for (AnnotationStore store : annotations){
      Constraint constraint = createConstraintFromAnnotation(store);
      if (constraint != null){
        Object valueToValidate = null;
        try {
          valueToValidate = type.invoke(object, getterName, null);
        } catch (Exception e) {
          throw new RuntimeException("Can't get field value, please makesure getter exists. " + propertyName + "\n Message: " + e.getMessage());
        }
        
        if (!constraint.isValid(valueToValidate)){
          InvalidConstraintImpl<T> ic = new InvalidConstraintImpl<T>(object.getClass(), null,
              store.getValue("message"), propertyName, object, valueToValidate);
          icSet.add(ic);
        }
      }
    }
  }

  private Constraint createConstraintFromAnnotation(AnnotationStore store) {
    AnnotationStore storeConstraintValidator = ReflectionUtils.getMetaAnnotation(store, ConstraintValidator.class);
    if (storeConstraintValidator == null)
      return null;
    
    String className = storeConstraintValidator.getValue("value");
    ClassType ctConstraintValidator = TypeOracle.Instance.getClassType(className);
    if (ctConstraintValidator == null)
      ValidateUtils.reflectionRequired(className, "To make ConstraintValidator works with GWT, please makesure its reflection enabled.");
    Class clazz = ctConstraintValidator.getDeclaringClass();
    Constraint constraint = ConstraintFactoryCacheImpl.getInstance().getInstance(clazz);
    constraint.initialize(store.allValues());
    return constraint;
  }



  public Set<InvalidConstraint<T>> validateValue(String propertyName,
      Object value, String... groups) {
    notSupportYet();
    return null;
  }
  
  

}
