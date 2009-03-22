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

import com.gwtent.client.reflection.AccessDef;
import com.gwtent.client.reflection.AnnotationStore;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.HasAnnotations;
import com.gwtent.client.reflection.HasMetaData;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.Type;


public class FieldImpl implements Field, HasMetaData, AccessDef, HasAnnotations{


	private final ClassType enclosingType;
	
	private final Annotations annotations = new Annotations();

	  private int modifierBits;

	  private final String name;

	  //TODO not support yet(in ReflectionCreator), use typename insteed
	  private Type type;
	  private String typeName;
	  
	  private final HasMetaData metaData = new MetaData();

	  public FieldImpl(ClassTypeImpl enclosingType, String name) {
	    this.enclosingType = enclosingType;
	    this.name = name;

//	    assert (enclosingType != null);
	    enclosingType.addField(this);
	  }

	  public void addModifierBits(int modifierBits) {
	    this.modifierBits |= modifierBits;
	  }

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Field#getEnclosingType()
	 */
	public ClassType getEnclosingType() {
	    return enclosingType;
	  }

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Field#getName()
	 */
	public String getName() {
//	    assert (name != null);
	    return name;
	  }

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Field#getType()
	 */
	public Type getType() {
//	    assert (type != null);
	    return type;
	  }

	  public boolean isDefaultAccess() {
	    return 0 == (modifierBits & (TypeOracleImpl.MOD_PUBLIC | TypeOracleImpl.MOD_PRIVATE | TypeOracleImpl.MOD_PROTECTED));
	  }

	  public boolean isFinal() {
	    return 0 != (modifierBits & TypeOracleImpl.MOD_FINAL);
	  }

	  public boolean isPrivate() {
	    return 0 != (modifierBits & TypeOracleImpl.MOD_PRIVATE);
	  }

	  public boolean isProtected() {
	    return 0 != (modifierBits & TypeOracleImpl.MOD_PROTECTED);
	  }

	  public boolean isPublic() {
	    return 0 != (modifierBits & TypeOracleImpl.MOD_PUBLIC);
	  }

	  public boolean isStatic() {
	    return 0 != (modifierBits & TypeOracleImpl.MOD_STATIC);
	  }

	  public boolean isTransient() {
	    return 0 != (modifierBits & TypeOracleImpl.MOD_TRANSIENT);
	  }

	  public boolean isVolatile() {
	    return 0 != (modifierBits & TypeOracleImpl.MOD_VOLATILE);
	  }

	  public void setType(Type type) {
	    this.type = type;
	  }

	public void addMetaData(String tagName, String[] values) {
		metaData.addMetaData(tagName, values);
		
	}

	public String[][] getMetaData(String tagName) {
		return metaData.getMetaData(tagName);
	}

	public String[] getMetaDataTags() {
		return metaData.getMetaDataTags();
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
  public AnnotationStore[] getAnnotations() {
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

	public Object getFieldValue(Object instance) {
		Method getter = this.getEnclosingType().findMethod(getGetterName(), new String[]{});
		if (getter != null){
			return getter.invoke(instance, new Object[]{});
		}else{
			throw new RuntimeException("Can not found getter of field (" + getName() + ").");
		}
	}
	
	public String getGetterName(){
		return "get" + getName().substring(0, 1).toUpperCase() + getName().substring(1);
	}
	
	public String getSetterName(){
	  return "set" + getName().substring(0, 1).toUpperCase() + getName().substring(1);
	}

	public void setFieldValue(Object instance, Object value) {
		Method setter = this.getEnclosingType().findMethod(getSetterName(), new String[]{});
		if (setter != null){
			setter.invoke(instance, new Object[]{value});
		}else{
			throw new RuntimeException("Can not found setter of field (" + getName() + ").");
		}
	}
}
