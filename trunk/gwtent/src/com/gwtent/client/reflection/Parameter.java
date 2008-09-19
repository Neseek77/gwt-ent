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
package com.gwtent.client.reflection;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

public class Parameter implements HasAnnotations {
	private final String name;
	
	private final Annotations annotations = new Annotations();

	private Type type;

	private String typeName;

	private final Method enclosingMethod;


	public Parameter(Method enclosingMethod, String typeName, String name) {
		this.enclosingMethod = enclosingMethod;
		this.typeName = typeName;
		this.name = name;

		enclosingMethod.addParameter(this);
	}
	
	public Parameter(Method enclosingMethod, Type type, String name) {
		this(enclosingMethod, getTypeName(type), name);
		
		this.type = type;
	}
	
	public static String getTypeName(Type type){
		if (type != null)
			return type.getQualifiedSourceName();
		else
			return "";
	}


	public Method getEnclosingMethod() {
		return enclosingMethod;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(type.getQualifiedSourceName());
		sb.append(" ");
		sb.append(getName());
		return sb.toString();
	}

	// Called when parameter types are found to be parameterized
	void setType(Type type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

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
