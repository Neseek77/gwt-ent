package com.gwtent.client.reflection;

import com.gwtent.client.reflection.impl.PrimitiveTypeImpl;

public interface PrimitiveType extends Type {

	public static final PrimitiveType BOOLEAN = PrimitiveTypeImpl.create("boolean", "Z");
	public static final PrimitiveType BYTE = PrimitiveTypeImpl.create("byte", "B");
	public static final PrimitiveType CHAR = PrimitiveTypeImpl.create("char", "C");
	public static final PrimitiveType DOUBLE = PrimitiveTypeImpl.create("double", "D");
	public static final PrimitiveType FLOAT = PrimitiveTypeImpl.create("float", "F");
	public static final PrimitiveType INT = PrimitiveTypeImpl.create("int", "I");
	public static final PrimitiveType LONG = PrimitiveTypeImpl.create("long", "J");
	public static final PrimitiveType SHORT = PrimitiveTypeImpl.create("short", "S");
	public static final PrimitiveType VOID = PrimitiveTypeImpl.create("void", "V");

}