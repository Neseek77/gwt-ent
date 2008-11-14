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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwtent.client.reflection.AbstractMethod;
import com.gwtent.client.reflection.AnnotationStore;
import com.gwtent.client.reflection.ArrayType;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Constructor;
import com.gwtent.client.reflection.HasAnnotations;
import com.gwtent.client.reflection.HasMetaData;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.Parameter;
import com.gwtent.client.reflection.Type;

public abstract class AbstractMethodImpl implements HasMetaData, HasAnnotations, AbstractMethod {

	private boolean isVarArgs = false;

	private final HasMetaData metaData = new MetaData();
	
	private final Annotations annotations =  new Annotations();


	private int modifierBits;
	private final String name;

	private final List params = new ArrayList();

	private final List thrownTypes = new ArrayList();

	private final List typeParams = new ArrayList();

	// Only the builder can construct
	AbstractMethodImpl(String name) {
		this.name = name;
	}

	public void addMetaData(String tagName, String[] values) {
		metaData.addMetaData(tagName, values);
	}

	public void addModifierBits(int bits) {
		modifierBits |= bits;
	}

	public void addThrows(Type type) {
		thrownTypes.add(type);
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.AbstractMethod#findParameter(java.lang.String)
	 */
	public Parameter findParameter(String name) {
		Iterator iterator = params.iterator();
		Parameter param = null;
		while (iterator.hasNext()) {
			param = (Parameter) iterator.next();
			if (param.getName().equals(name)) {
				return param;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.AbstractMethod#getEnclosingType()
	 */
	public abstract ClassType getEnclosingType();

	public String[][] getMetaData(String tagName) {
		return metaData.getMetaData(tagName);
	}

	public String[] getMetaDataTags() {
		return metaData.getMetaDataTags();
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.AbstractMethod#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.AbstractMethod#getParameters()
	 */
	public Parameter[] getParameters() {
		return (Parameter[]) params.toArray(TypeOracleImpl.NO_JPARAMS);
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.AbstractMethod#getReadableDeclaration()
	 */
	public abstract String getReadableDeclaration();

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.AbstractMethod#getThrows()
	 */
	public Type[] getThrows() {
		return (Type[]) thrownTypes.toArray(TypeOracleImpl.NO_JTYPES);
	}

	// public TypeParameter[] getTypeParameters() {
	// return typeParams.toArray(new TypeParameter[typeParams.size()]);
	// }

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.AbstractMethod#isConstructor()
	 */
	public abstract Constructor isConstructor();

	public boolean isDefaultAccess() {
		return 0 == (modifierBits & (TypeOracleImpl.MOD_PUBLIC
				| TypeOracleImpl.MOD_PRIVATE | TypeOracleImpl.MOD_PROTECTED));
	}

	public abstract Method isMethod();

	public boolean isPrivate() {
		return 0 != (modifierBits & TypeOracleImpl.MOD_PRIVATE);
	}

	public boolean isProtected() {
		return 0 != (modifierBits & TypeOracleImpl.MOD_PROTECTED);
	}

	public boolean isPublic() {
		return 0 != (modifierBits & TypeOracleImpl.MOD_PUBLIC);
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.AbstractMethod#isVarArgs()
	 */
	public boolean isVarArgs() {
		return isVarArgs;
	}

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.AbstractMethod#setVarArgs()
	 */
	public void setVarArgs() {
		isVarArgs = true;
	}

	protected int getModifierBits() {
		return modifierBits;
	}

	protected void toStringParamsAndThrows(StringBuffer sb) {
		sb.append("(");
		boolean needComma = false;
		for (int i = 0, c = params.size(); i < c; ++i) {
			Parameter param = (Parameter) params.get(i);
			if (needComma) {
				sb.append(", ");
			} else {
				needComma = true;
			}
			if ((isVarArgs() && i == c - 1) && (param.getType() != null)) {
				ArrayType arrayType = param.getType().isArray();
				assert (arrayType != null);
				sb.append(arrayType.getComponentType().getParameterizedQualifiedSourceName());
				sb.append("...");
			} else {
				sb.append(param.getTypeName());
			}
			sb.append(" ");
			sb.append(param.getName());
		}
		sb.append(")");

		if (!thrownTypes.isEmpty()) {
			sb.append(" throws ");
			needComma = false;
			Iterator iterator = thrownTypes.iterator();
			while (iterator.hasNext()) {
				TypeImpl thrownType = (TypeImpl) iterator.next();
				if (needComma) {
					sb.append(", ");
				} else {
					needComma = true;
				}
				sb.append(thrownType.getParameterizedQualifiedSourceName());
			}
		}
	}

	// protected void toStringTypeParams(StringBuilder sb) {
	// sb.append("<");
	// boolean needComma = false;
	// for (TypeParameter typeParam : typeParams) {
	// if (needComma) {
	// sb.append(", ");
	// } else {
	// needComma = true;
	// }
	// sb.append(typeParam.getName());
	// sb.append(typeParam.getBounds().toString());
	// }
	// sb.append(">");
	// }

	void addParameter(Parameter param) {
		params.add(param);
	}

	// void addTypeParameter(TypeParameter typeParameter) {
	// typeParams.add(typeParameter);
	// }

	boolean hasParamTypes(Type[] paramTypes) {
		if (params.size() != paramTypes.length) {
			return false;
		}

		for (int i = 0; i < paramTypes.length; i++) {
			Parameter candidate = (Parameter) params.get(i);
			// Identity tests are ok since identity is durable within an oracle.
			//
			if (candidate.getType() != paramTypes[i]) {
				return false;
			}
		}
		return true;
	}
	
	boolean hasParamTypesByTypeName(String[] paramTypes) {
		if (params.size() != paramTypes.length) {
			return false;
		}

		for (int i = 0; i < paramTypes.length; i++) {
			Parameter candidate = (Parameter) params.get(i);
			// Identity tests are ok since identity is durable within an oracle.
			//
			if (!candidate.getTypeName().equals(paramTypes[i])) {
				return false;
			}
		}
		return true;
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
  
  public String toString(){
  	return getReadableDeclaration();
  }
}