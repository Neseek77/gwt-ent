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

import java.util.HashMap;
import java.util.Map;

import com.gwtent.client.reflection.impl.TypeImpl;


public class PrimitiveTypeImpl extends TypeImpl implements Type, PrimitiveType {

	  private static Map map;

	  public static PrimitiveTypeImpl valueOf(String typeName) {
	    return (PrimitiveTypeImpl)getMap().get(typeName);
	  }

	  static PrimitiveType create(String name, String jni) {
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

	  
	  public ArrayType isArray() {
	    // intentional null
	    return null;
	  }

	  
	  public ClassType isClass() {
	    // intentional null
	    return null;
	  }

	  
//	  public JEnumType isEnum() {
//	    return null;
//	  }

	  
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
}
