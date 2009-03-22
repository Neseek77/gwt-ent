package com.gwtent.client.validate.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    	if (lstGroups.indexOf(str) < 0)
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
      doValidate(object, propertyName, type, icSet, getValidateAnnotationsAndOrder(field, lstGroups), "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
    }else{
      Method method = type.getMethod(propertyName, null);
      if (method != null)
        doValidate(object, propertyName, type, icSet, getValidateAnnotationsAndOrder(method, lstGroups), propertyName);
    }
    
    return icSet;
  }
  
  private List<AnnotationStore> getValidateAnnotationsAndOrder(HasAnnotations field, List<String> lstGroups){
  	List<AnnotationStore> lStore = new ArrayList<AnnotationStore>();
  	Map<String, List<AnnotationStore>> map = new HashMap<String, List<AnnotationStore>>();
  	for (String str : lstGroups)
  		map.put(str, new ArrayList<AnnotationStore>());
  	
  	for (AnnotationStore store : field.getAnnotations()){
  		if (isConstraintAnnotation(store)){
  			String[] groups = store.getAsStringArray("groups");
  			if ((groups == null) || (groups.length <= 0))
  				groups = new String[] {"default"};
  			else{
  				if (!stringExists(groups, "default"))
  					groups = appendArray(groups, "default");
  			}
  			
  			for (String group : groups){
  				if (lstGroups.indexOf(group) >= 0){
  					map.get(group).add(store);
  				}
  			}
  			
  		}
  	}
  	
  	for (String str : lstGroups){
  		lStore.addAll(map.get(str));
  	}
  	
  	return lStore;
  }
  
  private boolean stringExists(String[] strs, String value){
  	for (String str : strs){
  		if (str.equals(value))
  			return true;
  	}
  	
  	return false;
  }
  
  private String[] appendArray(String[] strs, String value){
  	String[] result = new String[strs.length + 1];
  	for (int i = 0; i < strs.length; i++)
  		result[i] = strs[i];
  	result[result.length - 1] = value;
  	return result;
  }

  private void doValidate(T object, String propertyName, ClassType type,
      Set<InvalidConstraint<T>> icSet, List<AnnotationStore> annotations, String getterName) {
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
  
  private boolean isConstraintAnnotation(AnnotationStore store){
  	AnnotationStore storeConstraintValidator = ReflectionUtils.getMetaAnnotation(store, ConstraintValidator.class);
    return (storeConstraintValidator != null);
  }

  private Constraint createConstraintFromAnnotation(AnnotationStore store) {
  	if (!isConstraintAnnotation(store))
  		return null;
  	
    AnnotationStore storeConstraintValidator = ReflectionUtils.getMetaAnnotation(store, ConstraintValidator.class);
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
