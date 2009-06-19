package com.gwtent.client.validate.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintDescriptor;
import javax.validation.ConstraintValidator;

import com.gwtent.client.reflection.ReflectionUtils;

public class ConstraintDescriptorImpl<T extends Annotation> implements ConstraintDescriptor<T> {

	private final T annotation;
	private final Map<String, Object> attributes;
	//private Set<ConstraintDescriptor<?>>
	private List<Class<? extends ConstraintValidator<T, ?>>> validatorClasses = new ArrayList<Class<? extends ConstraintValidator<T, ?>>>();
	private Set<Class<?>> groupClasses = new HashSet<Class<?>>();
	
	public ConstraintDescriptorImpl(T annotation){
		this.annotation = annotation;
		this.attributes = ReflectionUtils.getAnnotationValues(annotation);
	}
	
	public T getAnnotation() {
		return annotation;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public Set<ConstraintDescriptor<?>> getComposingConstraints() {
		return null;
	}

	public List<Class<? extends ConstraintValidator<T, ?>>> getConstraintValidatorClasses() {
		return validatorClasses;
	}
	
	public void addValidatorClass(Class<? extends ConstraintValidator<T, ?>> clazz){
		validatorClasses.add(clazz);
	}

	public Set<Class<?>> getGroups() {
		return groupClasses;
	}
	
	public void addGroupClass(Class<?> clazz){
		groupClasses.add(clazz);
	}
	
	public void addGroupClasses(Class<?>... groups){
		if (groups != null){
			for (Class<?> clazz : groups){
				addGroupClass(clazz);
			}
		}
	}

	public boolean isReportAsSingleViolation() {
		//Not support yet
		return false;
	}

}
