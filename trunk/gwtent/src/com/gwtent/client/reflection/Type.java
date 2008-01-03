package com.gwtent.client.reflection;



public abstract class Type {
	public Type(){
		
	}
	
	  public abstract String getJNISignature();

	  public Type getLeafType() {
	    return this;
	  }

	  public String getParameterizedQualifiedSourceName() {
	    return getQualifiedSourceName();
	  }

	  public abstract String getQualifiedSourceName();

	  public abstract String getSimpleSourceName();

	  public abstract ArrayType isArray();

	  public abstract ClassType isClass();

	  public ClassType isClassOrInterface() {
	    ClassType type = isClass();
	    if (type != null) {
	      return type;
	    }
	    return isInterface();
	  }

	  public abstract ClassType isInterface();

	  //public abstract ParameterizedType isParameterized();

	  public abstract PrimitiveType isPrimitive();
}