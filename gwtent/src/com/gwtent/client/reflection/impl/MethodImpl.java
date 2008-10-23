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

import com.gwtent.client.reflection.AbstractMethod;
import com.gwtent.client.reflection.AccessDef;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.HasAnnotations;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.Type;
import com.gwtent.client.reflection.TypeOracle;


public class MethodImpl extends AbstractMethodImpl implements AccessDef, HasAnnotations, AbstractMethod, Method {

	private final ClassTypeImpl enclosingType;

	public MethodImpl(ClassTypeImpl enclosingType, String name) {
		super(name);
		this.enclosingType = enclosingType;
		enclosingType.addMethod(this);
	}

	public ClassType getEnclosingType() {
		return enclosingType;
	}
	
	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Method#invoke(java.lang.Object, java.lang.Object[])
	 */
	public Object invoke(Object instance, Object[] args) throws RuntimeException {
		return enclosingType.invoke(instance, this.getName(), args);
	}


	//TODO not support yet
	private TypeImpl returnType;
	private String returnTypeName;

	/* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Method#getReturnType()
	 */
	public Type getReturnType() {
		if (returnType == null)
			returnType = (TypeImpl)TypeOracle.Instance.getClassType(returnTypeName);
		
		return returnType;
	}

	public boolean isAbstract() {
		return 0 != (getModifierBits() & TypeOracleImpl.MOD_ABSTRACT);
	}

	public boolean isFinal() {
		return 0 != (getModifierBits() & TypeOracleImpl.MOD_FINAL);
	}

	public Method isMethod() {
		return this;
	}

	public boolean isNative() {
		return 0 != (getModifierBits() & TypeOracleImpl.MOD_NATIVE);
	}

	public boolean isStatic() {
		return 0 != (getModifierBits() & TypeOracleImpl.MOD_STATIC);
	}

	

	public void setReturnType(TypeImpl type) {
		returnType = type;
	}




	public String getReadableDeclaration() {
		return getReadableDeclaration(getModifierBits());
	}

	public String getReadableDeclaration(boolean noAccess, boolean noNative,
			boolean noStatic, boolean noFinal, boolean noAbstract) {
		int bits = getModifierBits();
		if (noAccess) {
			bits &= ~(TypeOracleImpl.MOD_PUBLIC | TypeOracleImpl.MOD_PRIVATE | TypeOracleImpl.MOD_PROTECTED);
		}
		if (noNative) {
			bits &= ~TypeOracleImpl.MOD_NATIVE;
		}
		if (noStatic) {
			bits &= ~TypeOracleImpl.MOD_STATIC;
		}
		if (noFinal) {
			bits &= ~TypeOracleImpl.MOD_FINAL;
		}
		if (noAbstract) {
			bits &= ~TypeOracleImpl.MOD_ABSTRACT;
		}
		return getReadableDeclaration(bits);
	}

	String getReadableDeclaration(int modifierBits) {
		String[] names = TypeOracleImpl.modifierBitsToNames(modifierBits);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < names.length; i++) {
			sb.append(names[i]);
			sb.append(" ");
		}
		sb.append(this.returnTypeName);
		
		sb.append(" ");
		sb.append(getName());

		toStringParamsAndThrows(sb);

		return sb.toString();
	}

	public ConstructorImpl isConstructor() {
		return null;
	}

	public String getReturnTypeName() {
		return returnTypeName;
	}

	public void setReturnTypeName(String returnTypeName) {
		this.returnTypeName = returnTypeName;
	}

}