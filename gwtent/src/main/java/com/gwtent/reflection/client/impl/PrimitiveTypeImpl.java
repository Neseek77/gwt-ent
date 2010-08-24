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


package com.gwtent.reflection.client.impl;

import java.util.HashMap;
import java.util.Map;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.EnumType;
import com.gwtent.reflection.client.PrimitiveType;
import com.gwtent.reflection.client.Type;


public class PrimitiveTypeImpl extends TypeImpl implements Type, PrimitiveType {

	  private static Map map;

	  public static PrimitiveTypeImpl valueOf(String typeName) {
	    return (PrimitiveTypeImpl)getMap().get(typeName);
	  }

	  public static PrimitiveType create(String name, String jni) {
	    PrimitiveType type = new PrimitiveTypeImpl(name, jni);
	    Object existing = getMap().put(name, type);
	    assert (existing == null);
	    return type;
	  }

	  private static Map getMap() {
	    if (map == null) {
	      map = new HashMap();
	    }
	    return map;
	  }

	  private final String jni;

	  private final String name;

	  private PrimitiveTypeImpl(String name, String jni) {
	    this.name = name;
	    this.jni = jni;
	  }

	  public Type getErasedType() {
	    return this;
	  }

	  
	  public String getJNISignature() {
	    return jni;
	  }

	  
	  public String getQualifiedSourceName() {
	    return name;
	  }

	  
	  public String getSimpleSourceName() {
	    return name;
	  }

	  
	  public ArrayTypeImpl isArray() {
	    // intentional null
	    return null;
	  }

	  
	  public ClassType isClass() {
	    // intentional null
	    return null;
	  }

	  
	  public EnumType isEnum() {
	    return null;
	  }

	  
//	  public JGenericType isGenericType() {
//	    return null;
//	  }

	  
	  public ClassType isInterface() {
	    // intentional null
	    return null;
	  }

	  
//	  public JParameterizedType isParameterized() {
//	    // intentional null
//	    return null;
//	  }

	  
	  public PrimitiveType isPrimitive() {
	    return this;
	  }

	  
//	  public JRawType isRawType() {
//	    // intentional null
//	    return null;
//	  }

	  
//	  public JWildcardType isWildcard() {
//	    return null;
//	  }

	  
//	  PrimitiveType getSubstitutedType(JParameterizedType parameterizedType) {
//	    return this;
//	  }
	  
	  public String toString() {
	    return name;
	  }
}
