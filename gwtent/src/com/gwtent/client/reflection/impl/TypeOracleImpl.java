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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.NotFoundException;
import com.gwtent.client.reflection.Parameter;
import com.gwtent.client.reflection.PrimitiveType;
import com.gwtent.client.reflection.Type;
import com.gwtent.client.reflection.TypeOracle;

public class TypeOracleImpl implements TypeOracle {

	/**
	 * A reserved metadata tag to indicates that a field type, method return
	 * type or method parameter type is intended to be parameterized. Note that
	 * constructor type parameters are not supported at present.
	 */
	public static final String TAG_TYPEARGS = "gwt.typeArgs";

	public static final int MOD_ABSTRACT = 0x00000001;

	public static final int MOD_FINAL = 0x00000002;

	public static final int MOD_NATIVE = 0x00000004;

	public static final int MOD_PRIVATE = 0x00000008;

	public static final int MOD_PROTECTED = 0x00000010;

	public static final int MOD_PUBLIC = 0x00000020;

	public static final int MOD_STATIC = 0x00000040;

	public static final int MOD_TRANSIENT = 0x00000080;

	public static final int MOD_VOLATILE = 0x00000100;

	static final ClassTypeImpl[] NO_JCLASSES = new ClassTypeImpl[0];

	// static final JConstructor[] NO_JCTORS = new JConstructor[0];
	static final FieldImpl[] NO_JFIELDS = new FieldImpl[0];

	static final MethodImpl[] NO_JMETHODS = new MethodImpl[0];

	// static final Package[] NO_JPACKAGES = new Package[0];
	static final Parameter[] NO_JPARAMS = new Parameter[0];

	static final Type[] NO_JTYPES = new Type[0];

	static final String[][] NO_STRING_ARR_ARR = new String[0][];

	static final String[] NO_STRINGS = new String[0];

	static String combine(String[] strings, int startIndex) {
		StringBuffer sb = new StringBuffer();
		for (int i = startIndex; i < strings.length; i++) {
			String s = strings[i];
			sb.append(s);
		}
		return sb.toString();
	}

	static String[] modifierBitsToNames(int bits) {
		List strings = new ArrayList();

		// The order is based on the order in which we want them to appear.
		//
		if (0 != (bits & MOD_PUBLIC)) {
			strings.add("public");
		}

		if (0 != (bits & MOD_PRIVATE)) {
			strings.add("private");
		}

		if (0 != (bits & MOD_PROTECTED)) {
			strings.add("protected");
		}

		if (0 != (bits & MOD_STATIC)) {
			strings.add("static");
		}

		if (0 != (bits & MOD_ABSTRACT)) {
			strings.add("abstract");
		}

		if (0 != (bits & MOD_FINAL)) {
			strings.add("final");
		}

		if (0 != (bits & MOD_NATIVE)) {
			strings.add("native");
		}

		if (0 != (bits & MOD_TRANSIENT)) {
			strings.add("transient");
		}

		if (0 != (bits & MOD_VOLATILE)) {
			strings.add("volatile");
		}

		return (String[]) strings.toArray(NO_STRINGS);
	}

	
	
	public Type getType(String name) {
		Type type = findType(name);
		if (type == null) {
			if (PrimitiveType.BOOLEAN.getSimpleSourceName().equals(name))
				return PrimitiveType.BOOLEAN;
			else if (PrimitiveType.BYTE.getSimpleSourceName().equals(name))
				return PrimitiveType.BYTE;
			else if (PrimitiveType.CHAR.getSimpleSourceName().equals(name))
				return PrimitiveType.CHAR;
			else if (PrimitiveType.DOUBLE.getSimpleSourceName().equals(name))
				return PrimitiveType.DOUBLE;
			else if (PrimitiveType.FLOAT.getSimpleSourceName().equals(name))
				return PrimitiveType.FLOAT;
			else if (PrimitiveType.INT.getSimpleSourceName().equals(name))
				return PrimitiveType.INT;
			else if (PrimitiveType.LONG.getSimpleSourceName().equals(name))
				return PrimitiveType.LONG;
			else if (PrimitiveType.SHORT.getSimpleSourceName().equals(name))
				return PrimitiveType.SHORT;
			else if (PrimitiveType.VOID.getSimpleSourceName().equals(name))
				return PrimitiveType.VOID;
		}
		return type;
	}
	
	public static Type findType(String name) {
		Type type = typeMap.get(name);
		return type;
	}

	public ClassType getClassType(String name) {
		Type type = findType(name);
		if (type != null)
			return type.isClass();
		else
			return null;
	}

	public static void putType(Type type) {
		putType(type, type.getQualifiedSourceName());
	}

	public static void putType(Type type, String qualifiedSourceName) {
		typeMap.put(qualifiedSourceName, type);
	}

	private static Map<String, Type> typeMap = new HashMap<String, Type>();

	public ClassType getClassType(Class<?> classz) {
		return getClassType(classz.getName().replace('$', '.'));
	}
}
