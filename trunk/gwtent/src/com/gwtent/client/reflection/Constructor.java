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

import java.util.Map;


public class Constructor  extends AbstractMethod {
	  private final ClassType enclosingType;

	  public Constructor(ClassType enclosingType, String name) {
	    super(name);
	    this.enclosingType = enclosingType;
	    enclosingType.addConstructor(this);
	  }

	  public ClassType getEnclosingType() {
	    return enclosingType;
	  }

	  public String getReadableDeclaration() {
	    String[] names = TypeOracle.modifierBitsToNames(getModifierBits());
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < names.length; i++) {
	      sb.append(names[i]);
	      sb.append(" ");
	    }
//	    if (getTypeParameters().length > 0) {
//	      toStringTypeParams(sb);
//	      sb.append(" ");
//	    }
	    sb.append(getName());
	    toStringParamsAndThrows(sb);
	    return sb.toString();
	  }

	  public Constructor isConstructor() {
	    return this;
	  }

	  public Method isMethod() {
	    return null;
	  }

	  public String toString() {
	    return getReadableDeclaration();
	  }
	}
