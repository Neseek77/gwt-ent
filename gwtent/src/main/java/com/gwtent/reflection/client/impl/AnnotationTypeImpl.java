package com.gwtent.reflection.client.impl;

import com.gwtent.reflection.client.AnnotationType;

/**
 * 
 * @author James Luo
 */
public class AnnotationTypeImpl extends ClassTypeImpl implements AnnotationType {
	
	public AnnotationTypeImpl(Class declaringClass) {
		super(declaringClass);
	}

	public AnnotationType isAnnotation() {
    return this;
  }

}
