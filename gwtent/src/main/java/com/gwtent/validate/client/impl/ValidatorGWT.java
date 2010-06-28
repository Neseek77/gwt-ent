package com.gwtent.validate.client.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Constraint;
import javax.validation.ConstraintDescriptor;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.ElementDescriptor;
import javax.validation.Validator;
import javax.validation.MessageInterpolator.Context;
import javax.validation.groups.Default;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Field;
import com.gwtent.reflection.client.HasAnnotations;
import com.gwtent.reflection.client.Method;
import com.gwtent.reflection.client.ReflectionUtils;
import com.gwtent.reflection.client.TypeOracle;
import com.gwtent.reflection.client.pathResolver.PathResolver;
import com.gwtent.reflection.client.pathResolver.PathResolver.ENullInPath;
import com.gwtent.validate.client.impl.ConstraintValidatorContextImpl.ErrorMsg;

public class ValidatorGWT implements Validator{

  public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
    ClassType type = TypeOracle.Instance.getClassType(object.getClass());
    if (type == null)
      ReflectionUtils.reflectionRequired(object.getClass());
    
    Set<ConstraintViolation<T>> icSet = new HashSet<ConstraintViolation<T>>();
    
    for (Field field : type.getFields()){
      icSet.addAll(validateProperty(object, field.getName(), groups));
    }
    
    for (Method method : type.getMethods()){
      icSet.addAll(validateProperty(object, method.getName(), groups));
    }
    
    return icSet;
  }

  public <T> Set<ConstraintViolation<T>> validateProperty(T object,
      String propertyName, Class<?>... groups) {
    List<Class<?>> lstGroups = new ArrayList<Class<?>>();
    for (Class<?> clazz : groups){
    	if (lstGroups.indexOf(clazz) < 0)
    		lstGroups.add(clazz);
    }
    if (lstGroups.size() <= 0)
      lstGroups.add(Default.class);
    
    ClassType type = TypeOracle.Instance.getClassType(object.getClass());
    if (type == null)
      ReflectionUtils.reflectionRequired(object.getClass());

    //Path of propertyName support
    type = PathResolver.getLastClassTypeByPath(object.getClass(),	propertyName);
    
    try {  //let end user handle it
			object = (T) PathResolver.getInstanceLastLevelByPath(object, propertyName);
		} catch (ENullInPath e) {
			throw new IllegalArgumentException(e.getMessage(), e);
			//return icSet;  //error in half of path?
		}
    propertyName = PathResolver.getLastElementByPath(propertyName);

    Set<ConstraintViolation<T>> icSet = new HashSet<ConstraintViolation<T>>();
    Field field = type.getField(propertyName);
    if (field != null){
      doValidate(object, propertyName, type, icSet, getValidateAnnotationsAndOrder(field, lstGroups), ReflectionUtils.getGetter(type, propertyName), groups);
    }else{
      Method method = type.getMethod(propertyName, null);
      if (method == null)
      	method = ReflectionUtils.getGetter(type, propertyName);
      
      if (method != null)
        doValidate(object, propertyName, type, icSet, getValidateAnnotationsAndOrder(method, lstGroups), method, groups);
    }
    
    return icSet;
  }
  

	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,
			String propertyName, Object value, Class<?>... groups) {
		List<Class<?>> lstGroups = new ArrayList<Class<?>>();
    for (Class<?> clazz : groups){
    	if (lstGroups.indexOf(clazz) < 0)
    		lstGroups.add(clazz);
    }
    if (lstGroups.size() <= 0)
      lstGroups.add(Default.class);
    
    ClassType type = TypeOracle.Instance.getClassType(beanType);
    if (type == null)
      ReflectionUtils.reflectionRequired(beanType);
    
    Set<ConstraintViolation<T>> icSet = new HashSet<ConstraintViolation<T>>();
    
    //Path of propertyName support
    if (propertyName != null){
    	type = PathResolver.getLastClassTypeByPath(beanType,	propertyName);
      propertyName = PathResolver.getLastElementByPath(propertyName);
      
      
      
      Field field = type.getField(propertyName);
      if (field != null){
        doValidate(null, beanType, propertyName, type, icSet, getValidateAnnotationsAndOrder(field, lstGroups), value, groups);
      }else{
        Method method = type.getMethod(propertyName, null);
        if (method == null)
        	method = ReflectionUtils.getGetter(type, propertyName);
        
        if (method != null)
          doValidate(null, beanType, propertyName, type, icSet, getValidateAnnotationsAndOrder(method, lstGroups), value, groups);
      }      
    }
    
    return icSet;
	}


	private boolean classExistsInArray(Class<?>[] groups, Class<?> clazz){
		for (Class<?> cla : groups){
			if (cla == clazz)
				return true;
		}
		return false;
	}
  
	private boolean classAssignable(Class<?> parentClass, List<Class<?>> classesToCheck){
		for (Class<?> clazz : classesToCheck){
			if (ReflectionUtils.isAssignable(parentClass, clazz))
				return true;
		}
		
		return false;
	}
	
  private List<Annotation> getValidateAnnotationsAndOrder(HasAnnotations field, List<Class<?>> requestGroups){
  	List<Annotation> lStore = new ArrayList<Annotation>();
  	//Map<Class<?>, List<Annotation>> map = new HashMap<Class<?>, List<Annotation>>();
  	//for (Class<?> clazz : requestGroups)
    //	map.put(clazz, new ArrayList<Annotation>());
  	
  	for (Annotation store : field.getAnnotations()){
  		if (isConstraintAnnotation(store)){
  			Class<?>[] annoGroups = (Class<?>[])ReflectionUtils.getAnnotationValueByName(store, "groups");
  			
  			if ((annoGroups == null) || (annoGroups.length <= 0))  //if nothing, put it Default.class
  				annoGroups = new Class<?>[] {Default.class};
  			
  			for (Class<?> annoGroup : annoGroups){
  				if (classAssignable(annoGroup, requestGroups)){
  					if (lStore.indexOf(store) < 0)
  						lStore.add(store);
  				}
  					
  			}
  			
  		}
  	}
  	
//  	for (Class<?> str : requestGroups){
//  		lStore.addAll(map.get(str));
//  	}
  	
  	return lStore;
  }
  
  private boolean stringExists(String[] strs, String value){
  	for (String str : strs){
  		if (str.equals(value))
  			return true;
  	}
  	
  	return false;
  }
  
  private Class<?>[] appendArray(Class<?>[] groups, Class<?> group){
  	Class<?>[] result = new Class<?>[groups.length + 1];
  	for (int i = 0; i < groups.length; i++)
  		result[i] = groups[i];
  	result[result.length - 1] = group;
  	return result;
  }

  /**
   * This function supposed:
   * propertyName is the property of object or beanType, not a path.
   * 
   * @param <T>
   * @param object Can be Null, if not instance object
   * @param beanType the Class of object
   * @param propertyName
   * @param type
   * @param icSet
   * @param annotations
   * @param valueToValidate
   */
  private <T> void doValidate(T object, Class<? extends Object> beanType, String propertyName, ClassType type,
      Set<ConstraintViolation<T>> icSet, List<Annotation> annotations, final Object valueToValidate, Class<?>... groups) {
  	for (Annotation store : annotations){
    	List<ConstraintValidator<?, ?>> validators = createConstraintValidatorFromAnnotation(store);
      if (validators != null){

      	if (validators.size() > 1)
        	throw new RuntimeException("For now, We only support ONE ConstraintValidator in Constraint.");
        
        for (ConstraintValidator<?, ?> validator : validators){
        	String defaultErrorMessage = ReflectionUtils.getAnnotationValueByName(store, "message").toString();
        	ConstraintValidatorContextImpl context = new ConstraintValidatorContextImpl(defaultErrorMessage);
        	
        	ConstraintValidator<?, Object> v = (ConstraintValidator<?, Object>) validator;
        	if (!v.isValid(valueToValidate, context)){
        		for (ErrorMsg msg : context.getMsgs()){
        			String messageTemplate = msg.getMessage();
        			
        			String message = getMessageFromInterpolator(valueToValidate,
									store, validator, messageTemplate, groups);
        			
        			String property = msg.getProperty();
        			if (property == null || property.length() <= 0)
        				property = propertyName;
      				ConstraintViolationImpl<T> ic = new ConstraintViolationImpl<T>(beanType, 
            			message, messageTemplate, property, object, valueToValidate);
              icSet.add(ic);
        		}
          }
        }
        
      }
    }
  }
  
  /**
   * Cache it
   */
  private MessageInterpolatorGWTImpl msgInterpolator = new MessageInterpolatorGWTImpl();

	private String getMessageFromInterpolator(final Object valueToValidate,
			Annotation store, ConstraintValidator<?, ?> validator,
			String messageTemplate, Class<?>... groups) {
		final ConstraintDescriptorImpl<Annotation> descriptor = new ConstraintDescriptorImpl<Annotation>(store); 
		descriptor.addGroupClasses(groups);
		descriptor.addValidatorClass((Class<? extends ConstraintValidator<Annotation, ?>>) validator.getClass());
		
		String message = msgInterpolator.interpolate(messageTemplate, new Context(){

			public ConstraintDescriptor<?> getConstraintDescriptor() {
				return descriptor;
			}

			public Object getValidatedValue() {
				return valueToValidate;
			}
		});
		return message;
	}
  
  private <T> void doValidate(T object, String propertyName, ClassType type,
      Set<ConstraintViolation<T>> icSet, List<Annotation> annotations, Method getter, Class<?>... groups) {
  	Object valueToValidate = null;
    if (getter == null){
    	throw new RuntimeException("getter not exists in class(" + type.getName() + ") for property(" + propertyName + ")");
    }
    
    if (annotations == null || annotations.size() <= 0)
    	return;
    
    try {
      valueToValidate = getter.invoke(object);
    } catch (Exception e) {
    	throw new RuntimeException("Can't get field value, please makesure getter exists and can be invoke correctly. " + propertyName + "\n Message: " + e.getMessage());
    }
  	
  	doValidate(object, object.getClass(), propertyName, type, icSet, annotations, valueToValidate, groups);
  }
  
  private boolean isConstraintAnnotation(Annotation store){
  	Constraint storeConstraintValidator = ReflectionUtils.getMetaAnnotation(store, Constraint.class);
    return (storeConstraintValidator != null);
  }

  private List<ConstraintValidator<?, ?>> createConstraintValidatorFromAnnotation(Annotation store) {
  	if (!isConstraintAnnotation(store))
  		return null;
  	
  	List<ConstraintValidator<?, ?>> result = new ArrayList<ConstraintValidator<?, ?>>();
  	
  	Constraint storeConstraint = ReflectionUtils.getMetaAnnotation(store, Constraint.class);
  	
  	for (Class<? extends ConstraintValidator<?, ?>> clazz : storeConstraint.validatedBy()){
  		ClassType ctConstraintValidator = TypeOracle.Instance.getClassType(clazz);
      if (ctConstraintValidator == null)
        ReflectionUtils.reflectionRequired(clazz, "To make ConstraintValidator works with GWT, please makesure its reflection enabled.");
      
      ConstraintValidator<Annotation, ?> validator = (ConstraintValidator<Annotation, ?>) ConstraintFactoryCacheImpl.getInstance().getInstance(clazz);
      validator.initialize(store);
      result.add(validator);
  	}
    
    return result;
  }



  
  

}
