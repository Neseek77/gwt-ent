/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


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