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

import com.gwtent.client.reflection.ArrayType;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.PrimitiveType;
import com.gwtent.client.reflection.Type;



public abstract class TypeImpl implements Type {
	public TypeImpl(){
		
	}
	
	  public abstract String getJNISignature();

	  public Type getLeafType() {
	    return this;
	  }

	  public String getParameterizedQualifiedSourceName() {
	    return getQualifiedSourceName();
	  }

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#getQualifiedSourceName()
	 */
	public abstract String getQualifiedSourceName();

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#getSimpleSourceName()
	 */
	public abstract String getSimpleSourceName();

	public abstract ArrayType isArray();

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#isClass()
	 */
	public abstract ClassType isClass();

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#isClassOrInterface()
	 */
	public ClassType isClassOrInterface() {
	    ClassType type = isClass();
	    if (type != null) {
	      return type;
	    }
	    return isInterface();
	  }

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#isInterface()
	 */
	public abstract ClassType isInterface();

	  //public abstract ParameterizedType isParameterized();

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#isPrimitive()
	 */
	public abstract PrimitiveType isPrimitive();
}