package com.gwtent.reflection.client;

import java.lang.annotation.Annotation;

/**
 * Try to simulate the most of functions in java.lang.Class
 * 
 * @author James Luo
 * 
 */
public class ClassHelper<T> implements AnnotatedElement {

	private final ClassType classType;
	private final EnumType enumType;

	private final Type type;
	private final Class<?> clazz;

	private ClassHelper(Type type, Class<?> clazz) {
		this.type = type;
		this.clazz = clazz;

		this.classType = type.isClassOrInterface();
		this.enumType = type.isEnum();
	}

	/**
	 * Supported:
	 * <p>
	 * <ul>
	 * <li>class</li>
	 * <li>interface</li>
	 * <li>enum</li>
	 * <li>array</li>
	 * <li>primitive types</li>
	 * </ul>
	 * </p>
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T> ClassHelper<T> AsClass(Class<T> clazz) {
		ClassType classType = TypeOracle.Instance.getClassType(clazz);

		if (classType == null) {
			ReflectionUtils.checkReflection(clazz);
		}

		return new ClassHelper<T>(classType, clazz);
	}

	public Type getType() {
		return type;
	}

	/**
	 * Returns the <code>Class</code> representing the superclass of the entity
	 * (class, interface, primitive type or void) represented by this
	 * <code>Class</code>. If this <code>Class</code> represents either the
	 * <code>Object</code> class, an interface, a primitive type, or void, then
	 * null is returned. If this object represents an array class then the
	 * <code>Class</code> object representing the <code>Object</code> class is
	 * returned.
	 * 
	 * @return the superclass of the class represented by this object.
	 */
	public Class<? super T> getSuperclass() {
		ClassType result = null;
		if (type instanceof ClassType)
			result = ((ClassType) type).getSuperclass();

		if (result == null)
			return null;
		else
			return (Class<? super T>) result.getDeclaringClass();
	}

	/**
	 * Determines the interfaces implemented by the class or interface represented
	 * by this object.
	 * 
	 * <p>
	 * If this object represents a class, the return value is an array containing
	 * objects representing all interfaces implemented by the class. The order of
	 * the interface objects in the array corresponds to the order of the
	 * interface names in the <code>implements</code> clause of the declaration of
	 * the class represented by this object. For example, given the declaration:
	 * <blockquote>
	 * 
	 * <pre>
	 * class Shimmer implements FloorWax, DessertTopping { ... }
	 * </pre>
	 * 
	 * </blockquote> suppose the value of <code>s</code> is an instance of
	 * <code>Shimmer</code>; the value of the expression: <blockquote>
	 * 
	 * <pre>
	 * s.getClass().getInterfaces()[0]
	 * </pre>
	 * 
	 * </blockquote> is the <code>Class</code> object that represents interface
	 * <code>FloorWax</code>; and the value of: <blockquote>
	 * 
	 * <pre>
	 * s.getClass().getInterfaces()[1]
	 * </pre>
	 * 
	 * </blockquote> is the <code>Class</code> object that represents interface
	 * <code>DessertTopping</code>.
	 * 
	 * <p>
	 * If this object represents an interface, the array contains objects
	 * representing all interfaces extended by the interface. The order of the
	 * interface objects in the array corresponds to the order of the interface
	 * names in the <code>extends</code> clause of the declaration of the
	 * interface represented by this object.
	 * 
	 * <p>
	 * If this object represents a class or interface that implements no
	 * interfaces, the method returns an array of length 0.
	 * 
	 * <p>
	 * If this object represents a primitive type or void, the method returns an
	 * array of length 0.
	 * 
	 * @return an array of interfaces implemented by this class.
	 */
	public Class<?>[] getInterfaces() {
		ClassType<?>[] types = new ClassType<?>[0];
		if (type instanceof ClassType) {
			types = ((ClassType<?>) type).getImplementedInterfaces();
		}

		Class<?>[] result = new Class<?>[types.length];
		for (int i = 0; i < types.length; i++) {
			result[i] = types[i].getDeclaringClass();
		}
		return result;
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		if (type instanceof HasAnnotations) {
			return ((HasAnnotations) type).getAnnotation(annotationClass);
		}

		return null;
	}

	public Annotation[] getAnnotations() {
		if (type instanceof HasAnnotations) {
			return ((HasAnnotations) type).getAnnotations();
		}

		return new Annotation[0];
	}

	public Annotation[] getDeclaredAnnotations() {
		if (type instanceof HasAnnotations) {
			return ((HasAnnotations) type).getDeclaredAnnotations();
		}

		return new Annotation[0];
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		if (type instanceof HasAnnotations) {
			return ((HasAnnotations) type).isAnnotationPresent(annotationClass);
		}

		return false;
	}

	/**
	 * Determines if the specified <code>Class</code> object represents an
	 * interface type.
	 * 
	 * @return <code>true</code> if this object represents an interface;
	 *         <code>false</code> otherwise.
	 */
	public boolean isInterface() {
		return type.isInterface() != null;
	}

	/**
	 * Determines if the specified <code>Class</code> object represents a
	 * primitive type.
	 * 
	 * <p>
	 * There are nine predefined <code>Class</code> objects to represent the eight
	 * primitive types and void. These are created by the Java Virtual Machine,
	 * and have the same names as the primitive types that they represent, namely
	 * <code>boolean</code>, <code>byte</code>, <code>char</code>,
	 * <code>short</code>, <code>int</code>, <code>long</code>, <code>float</code>
	 * , and <code>double</code>.
	 * 
	 * <p>
	 * These objects may only be accessed via the following public static final
	 * variables, and are the only <code>Class</code> objects for which this
	 * method returns <code>true</code>.
	 * 
	 * @return true if and only if this class represents a primitive type
	 * 
	 * @see java.lang.Boolean#TYPE
	 * @see java.lang.Character#TYPE
	 * @see java.lang.Byte#TYPE
	 * @see java.lang.Short#TYPE
	 * @see java.lang.Integer#TYPE
	 * @see java.lang.Long#TYPE
	 * @see java.lang.Float#TYPE
	 * @see java.lang.Double#TYPE
	 * @see java.lang.Void#TYPE
	 */
	public boolean isPrimitive() {
		return type.isPrimitive() != null;
	}

	/**
	 * Returns the <code>Class</code> object associated with the class or
	 * interface with the given string name. Invoking this method is equivalent
	 * to:
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * Class.forName(className, true, currentLoader)
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * where <code>currentLoader</code> denotes the defining class loader of the
	 * current class.
	 * 
	 * <p>
	 * For example, the following code fragment returns the runtime
	 * <code>Class</code> descriptor for the class named
	 * <code>java.lang.Thread</code>:
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 *   Class t = Class.forName(&quot;java.lang.Thread&quot;)
	 * </pre>
	 * 
	 * </blockquote>
	 * <p>
	 * A call to <tt>forName("X")</tt> causes the class named <tt>X</tt> to be
	 * initialized.
	 * 
	 * @param className
	 *          the fully qualified name of the desired class.
	 * @return the <code>Class</code> object for the class with the specified
	 *         name.
	 * @exception com.gwtent.reflection.client.ReflectionRequiredException
	 *              as the reason of ClassNotFoundException
	 * @exception ClassNotFoundException
	 *              if the class cannot be located
	 */
	public static Class<?> forName(String className)
			throws ClassNotFoundException {
		ClassType type = TypeOracle.Instance.getClassType(className);
		if (type == null) {
			try {
				ReflectionUtils.checkReflection(className);
			} catch (ReflectionRequiredException e) {
				throw new ClassNotFoundException("Class not found: " + className, e);
			}

		}

		return type.getDeclaringClass();
	}

	/**
	 * Determines if this <code>Class</code> object represents an array class.
	 * 
	 * @return <code>true</code> if this object represents an array class;
	 *         <code>false</code> otherwise.
	 * @since GWTENT RC2
	 */
	public boolean isArray() {
		return clazz.isArray();
	}

	/**
	 * Returns the <code>Class</code> representing the component type of an array.
	 * If this class does not represent an array class this method returns null.
	 * 
	 * @return the <code>Class</code> representing the component type of this
	 *         class if this class is an array
	 * @see java.lang.reflect.Array
	 * @since GWTENT RC2
	 */
	public Class<?> getComponentType() {
		return clazz.getComponentType();
	}

	/**
	 * Returns the <tt>Type</tt> representing the direct superclass of the entity
	 * (class, interface, primitive type or void) represented by this
	 * <tt>Class</tt>.
	 * 
	 * <p>
	 * If the superclass is a parameterized type, the <tt>Type</tt> object
	 * returned must accurately reflect the actual type parameters used in the
	 * source code. The parameterized type representing the superclass is created
	 * if it had not been created before. See the declaration of
	 * {@link java.lang.reflect.ParameterizedType ParameterizedType} for the
	 * semantics of the creation process for parameterized types. If this
	 * <tt>Class</tt> represents either the <tt>Object</tt> class, an interface, a
	 * primitive type, or void, then null is returned. If this object represents
	 * an array class then the <tt>Class</tt> object representing the
	 * <tt>Object</tt> class is returned.
	 * 
	 * @throws GenericSignatureFormatError
	 *           if the generic class signature does not conform to the format
	 *           specified in the Java Virtual Machine Specification, 3rd edition
	 * @throws TypeNotPresentException
	 *           if the generic superclass refers to a non-existent type
	 *           declaration
	 * @throws MalformedParameterizedTypeException
	 *           if the generic superclass refers to a parameterized type that
	 *           cannot be instantiated for any reason
	 * @return the superclass of the class represented by this object
	 * @since 1.5
	 */
	public Type getGenericSuperclass() {
		if (type.isParameterized() != null) {
			// Historical irregularity:
			// Generic signature marks interfaces with superclass = Object
			// but this API returns null for interfaces
			if (isInterface())
				return null;
			
			return type.isParameterized().isClass().getSuperclass();
		} else
			return classType == null ? null : classType.getSuperclass();
	}
	
	/**
   * Returns the <tt>Type</tt>s representing the interfaces 
   * directly implemented by the class or interface represented by
   * this object.
   *
   * <p>If a superinterface is a parameterized type, the
   * <tt>Type</tt> object returned for it must accurately reflect
   * the actual type parameters used in the source code. The
   * parameterized type representing each superinterface is created
   * if it had not been created before. See the declaration of
   * {@link java.lang.reflect.ParameterizedType ParameterizedType}
   * for the semantics of the creation process for parameterized
   * types.
   *
   * <p> If this object represents a class, the return value is an
   * array containing objects representing all interfaces
   * implemented by the class. The order of the interface objects in
   * the array corresponds to the order of the interface names in
   * the <tt>implements</tt> clause of the declaration of the class
   * represented by this object.  In the case of an array class, the
   * interfaces <tt>Cloneable</tt> and <tt>Serializable</tt> are
   * returned in that order.
   *
   * <p>If this object represents an interface, the array contains
   * objects representing all interfaces directly extended by the
   * interface.  The order of the interface objects in the array
   * corresponds to the order of the interface names in the
   * <tt>extends</tt> clause of the declaration of the interface
   * represented by this object.
   *
   * <p>If this object represents a class or interface that
   * implements no interfaces, the method returns an array of length
   * 0.
   *
   * <p>If this object represents a primitive type or void, the
   * method returns an array of length 0.
   * 
   * @return an array of interfaces implemented by this class
   * @since RC2
   */
  public Type[] getGenericInterfaces() {
  	ClassType<?>[] types = new ClassType<?>[0];
		if (type instanceof ClassType) {
			types = ((ClassType<?>) type).getImplementedInterfaces();
		}
		return types;
  }
}
