package com.gwtent.client.reflection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Method extends AbstractMethod implements AccessDef {

	private final ClassType enclosingType;

	public Method(ClassType enclosingType, String name) {
		super(name);
		this.enclosingType = enclosingType;
		enclosingType.addMethod(this);
	}

	public ClassType getEnclosingType() {
		return enclosingType;
	}
	
	public Object invoke(Object instance, String methodName, Object[] args) {
		return enclosingType.invoke(instance, methodName, args);
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