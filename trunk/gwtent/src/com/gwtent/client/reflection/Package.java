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
