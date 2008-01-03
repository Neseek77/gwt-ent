package com.gwtent.client.reflection;

import java.util.ArrayList;
import java.util.List;

public class TypeOracle {

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

	static final ClassType[] NO_JCLASSES = new ClassType[0];

	// static final JConstructor[] NO_JCTORS = new JConstructor[0];
	static final Field[] NO_JFIELDS = new Field[0];

	static final Method[] NO_JMETHODS = new Method[0];

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

}
