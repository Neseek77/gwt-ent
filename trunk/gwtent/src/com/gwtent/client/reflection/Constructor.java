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
