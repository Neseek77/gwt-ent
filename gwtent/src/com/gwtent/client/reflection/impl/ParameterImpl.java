/*
 * GwtEnt - Gwt ent library.
 * 
 * Copyright (c) 2007, James Luo(JamesLuo.au@gmail.com)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.gwtent.client.reflection.impl;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import com.gwtent.client.reflection.AnnotationStore;
import com.gwtent.client.reflection.HasAnnotations;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.Parameter;
import com.gwtent.client.reflection.Type;
import com.gwtent.client.reflection.TypeOracle;

public class ParameterImpl implements HasAnnotations, Parameter {
	private final String name;
	
	private final Annotations annotations = new Annotations();

	private Type type;

	private String typeName;

	private final Method enclosingMethod;


	public ParameterImpl(MethodImpl enclosingMethod, String typeName, String name) {
		this.enclosingMethod = enclosingMethod;
		this.typeName = typeName;
		this.name = name;

		enclosingMethod.addParameter(this);
	}
	
	public ParameterImpl(MethodImpl enclosingMethod, TypeImpl type, String name) {
		this(enclosingMethod, getTypeName(type), name);
		
		this.type = type;
	}
	
	public static String getTypeName(Type type){
		if (type != null)
			return type.getQualifiedSourceName();
		else
			return "";
	}


	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Parameter#getEnclosingMethod()
	 */
	public Method getEnclosingMethod() {
		return enclosingMethod;
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Parameter#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Parameter#getType()
	 */
	public Type getType() {
		if (type == null)
			type = TypeOracle.Instance.getType(typeName);
		
		return type;
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Parameter#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(type.getQualifiedSourceName());
		sb.append(" ");
		sb.append(getName());
		return sb.toString();
	}

	// Called when parameter types are found to be parameterized
	void setType(TypeImpl type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Parameter#getTypeName()
	 */
	public String getTypeName() {
		return typeName;
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Parameter#setTypeName(java.lang.String)
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
  public <T extends Annotation> AnnotationStore getAnnotation(Class<T> annotationClass) {
    return annotations.getAnnotation(annotationClass);
  }
  
  public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
    return annotations.isAnnotationPresent(annotationClass);
  }
  
  /**
   * NOTE: This method is for testing purposes only.
   */
  AnnotationStore[] getAnnotations() {
    return annotations.getAnnotations();
  }

  /**
   * NOTE: This method is for testing purposes only.
   */
  AnnotationStore[] getDeclaredAnnotations() {
    return annotations.getDeclaredAnnotations();
  }
  
  public void addAnnotations(
      List<AnnotationStore> annotations) {
    this.annotations.addAnnotations(annotations);
  }
}
