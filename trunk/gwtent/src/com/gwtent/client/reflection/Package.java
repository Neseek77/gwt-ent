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


public class Package {

	  private final String name;

	  private final Map types = new HashMap();

	  public Package(String name) {
	    this.name = name;
	  }

	  public ClassType findType(String typeName) {
	    String[] parts = typeName.split("\\.");
	    return findType(parts);
	  }

	  public ClassType findType(String[] typeName) {
	    return findTypeImpl(typeName, 0);
	  }

	  public String getName() {
	    return name;
	  }

	  public ClassType getType(String typeName) {
	    ClassType result = findType(typeName);
//	    if (result == null) {
//	      throw new NotFoundException();
//	    }
	    return result;
	  }

	  public ClassType[] getTypes() {
	    return (ClassType[]) types.values().toArray(TypeOracle.NO_JCLASSES);
	  }

	  public boolean isDefault() {
	    return "".equals(name);
	  }

	  public String toString() {
	    return "package " + name;
	  }

	  void addType(ClassType type) {
	    types.put(type.getSimpleSourceName(), type);
	  }

	  ClassType findTypeImpl(String[] typeName, int index) {
	    ClassType found = (ClassType) types.get(typeName[index]);
	    if (found == null) {
	      return null;
	    } else if (index < typeName.length - 1) {
	      return found.findNestedTypeImpl(typeName, index + 1);
	    } else {
	      return found;
	    }
	  }

	  void remove(ClassType type) {
	    Object removed = types.remove(type.getSimpleSourceName());
	    // JDT will occasionally remove non-existent items, such as packages.
	  }
}
