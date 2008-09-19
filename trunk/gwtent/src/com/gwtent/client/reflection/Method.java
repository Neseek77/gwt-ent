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


public class Method extends AbstractMethod implements AccessDef, HasAnnotations {

	private final ClassType enclosingType;

	public Method(ClassType enclosingType, String name) {
		super(name);
		this.enclosingType = enclosingType;
		enclosingType.addMethod(this);
	}

	public ClassType getEnclosingType() {
		return enclosingType;
	}
	
	public Object invoke(Object instance, Object[] args) {
		return enclosingType.invoke(instance, this.getName(), args);
	}


	//TODO not support yet
	private Type returnType;
	private String returnTypeName;

	public Type getReturnType() {
		return returnType;
	}

	public boolean isAbstract() {
		return 0 != (getModifierBits() & TypeOracle.MOD_ABSTRACT);
	}

	public boolean isFinal() {
		return 0 != (getModifierBits() & TypeOracle.MOD_FINAL);
	}

	public Method isMethod() {
		return this;
	}

	public boolean isNative() {
		return 0 != (getModifierBits() & TypeOracle.MOD_NATIVE);
	}

	public boolean isStatic() {
		return 0 != (getModifierBits() & TypeOracle.MOD_STATIC);
	}

	

	public void setReturnType(Type type) {
		returnType = type;
	}




	public String getReadableDeclaration() {
		return getReadableDeclaration(getModifierBits());
	}

	public String getReadableDeclaration(boolean noAccess, boolean noNative,
			boolean noStatic, boolean noFinal, boolean noAbstract) {
		int bits = getModifierBits();
		if (noAccess) {
			bits &= ~(TypeOracle.MOD_PUBLIC | TypeOracle.MOD_PRIVATE | TypeOracle.MOD_PROTECTED);
		}
		if (noNative) {
			bits &= ~TypeOracle.MOD_NATIVE;
		}
		if (noStatic) {
			bits &= ~TypeOracle.MOD_STATIC;
		}
		if (noFinal) {
			bits &= ~TypeOracle.MOD_FINAL;
		}
		if (noAbstract) {
			bits &= ~TypeOracle.MOD_ABSTRACT;
		}
		return getReadableDeclaration(bits);
	}

	String getReadableDeclaration(int modifierBits) {
		String[] names = TypeOracle.modifierBitsToNames(modifierBits);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < names.length; i++) {
			sb.append(names[i]);
			sb.append(" ");
		}
		sb.append(returnType.getParameterizedQualifiedSourceName());
		sb.append(" ");
		sb.append(getName());

		toStringParamsAndThrows(sb);

		return sb.toString();
	}

	public Constructor isConstructor() {
		return null;
	}

	public String getReturnTypeName() {
		return returnTypeName;
	}

	public void setReturnTypeName(String returnTypeName) {
		this.returnTypeName = returnTypeName;
	}

}