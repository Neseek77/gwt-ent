package com.gwtent.validate.client.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import com.gwtent.reflection.client.ClassHelper;
import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Field;
import com.gwtent.reflection.client.Method;
import com.gwtent.reflection.client.PrimitiveType;
import com.gwtent.reflection.client.ReflectionUtils;
import com.gwtent.reflection.client.Type;
import com.gwtent.reflection.client.TypeOracle;

/**
 * 
 * @author James Luo
 * 
 *         10/08/2010 3:42:22 PM
 */
public class ReflectionHelper {
	/**
	 * Private constructor in order to avoid instantiation.
	 */
	private ReflectionHelper() {
	}
	
	/**
	 * Get all superclasses and interfaces recursively.
	 *
	 * @param clazz The class to start the search with.
	 * @param classes List of classes to which to add all found super classes and interfaces.
	 */
	public static void computeClassHierarchy(Class<?> clazz, List<Class<?>> classes) {
		for ( Class current = clazz; current != null; current = ClassHelper.AsClass(current).getSuperclass() ) {
			if ( classes.contains( current ) ) {
				return;
			}
			classes.add( current );
			for ( Class currentInterface : ClassHelper.AsClass(current).getInterfaces()) {
				computeClassHierarchy( currentInterface, classes );
			}
		}
	}


	// run client in privileged block
	@SuppressWarnings("unchecked")
	static <T> T getAnnotationParameter(Annotation annotation,
			String parameterName, Class<T> type) {
		ReflectionUtils.checkReflection(annotation.annotationType());

		Method m = TypeOracle.Instance.getClassType(annotation.annotationType())
				.findMethod(parameterName);

		Object o = m.invoke(annotation);
		if (o.getClass().getName().equals(type.getName())) {
			return (T) o;
		} else {
			String msg = "Wrong parameter type. Expected: " + type.getName()
					+ " Actual: " + o.getClass().getName();
			throw new ValidationException(msg);
		}

	}
	
	
	/**
	 * Determines the type of elements of an <code>Iterable</code>, array or the value of a <code>Map</code>.
	 *
	 * @param type the type to inspect
	 *
	 * @return Returns the type of elements of an <code>Iterable</code>, array or the value of a <code>Map</code>. <code>
	 *         null</code> is returned in case the type is not indexable (in the context of JSR 303).
	 */
	public static Type getIndexedType(Type type) {
		Type indexedType = null;
//		if ( isIterable( type ) && type instanceof ParameterizedType ) {
//			ParameterizedType paramType = ( ParameterizedType ) type;
//			indexedType = paramType.getActualTypeArguments()[0];
//		}
//		else if ( isMap( type ) && type instanceof ParameterizedType ) {
//			ParameterizedType paramType = ( ParameterizedType ) type;
//			indexedType = paramType.getActualTypeArguments()[1];
//		}
//		else if ( TypeUtils.isArray( type ) ) {
//			indexedType = TypeUtils.getComponentType( type );
//		}
		return indexedType;
	}

	/**
	 * @param type the type to check.
	 *
	 * @return Returns <code>true</code> if <code>type</code> is a iterable type, <code>false</code> otherwise.
	 */
	public static boolean isIterable(Type type) {
		if ( type instanceof ClassType && extendsOrImplements( (( ClassType ) type).getDeclaringClass(), Iterable.class ) ) {
			return true;
		}
		
//		if ( type instanceof Class && extendsOrImplements( ( Class ) type, Iterable.class ) ) {
//			return true;
//		}
//		if ( type instanceof ParameterizedType ) {
//			return isIterable( ( ( ParameterizedType ) type ).getRawType() );
//		}
//		if ( type instanceof WildcardType ) {
//			Type[] upperBounds = ( ( WildcardType ) type ).getUpperBounds();
//			return upperBounds.length != 0 && isIterable( upperBounds[0] );
//		}
		return false;
	}

	/**
	 * @param type the type to check.
	 *
	 * @return Returns <code>true</code> if <code>type</code> is implementing <code>Map</code>, <code>false</code> otherwise.
	 */
	public static boolean isMap(Type type) {
		if ( type instanceof ClassType && extendsOrImplements( (( ClassType ) type).getDeclaringClass(), Map.class ) ) {
			return true;
		}
//		if ( type instanceof ParameterizedType ) {
//			return isMap( ( ( ParameterizedType ) type ).getRawType() );
//		}
//		if ( type instanceof WildcardType ) {
//			Type[] upperBounds = ( ( WildcardType ) type ).getUpperBounds();
//			return upperBounds.length != 0 && isMap( upperBounds[0] );
//		}
		return false;
	}

	/**
	 * @param type the type to check.
	 *
	 * @return Returns <code>true</code> if <code>type</code> is implementing <code>List</code>, <code>false</code> otherwise.
	 */
	public static boolean isList(Type type) {
		if ( type instanceof ClassType && extendsOrImplements( (( ClassType ) type).getDeclaringClass(), List.class ) ) {
			return true;
		}
//		if ( type instanceof ParameterizedType ) {
//			return isList( ( ( ParameterizedType ) type ).getRawType() );
//		}
//		if ( type instanceof WildcardType ) {
//			Type[] upperBounds = ( ( WildcardType ) type ).getUpperBounds();
//			return upperBounds.length != 0 && isList( upperBounds[0] );
//		}
		return false;
	}

	/**
	 * Tries to retrieve the indexed value from the specified object.
	 *
	 * @param value The object from which to retrieve the indexed value. The object has to be non <code>null</null> and
	 * either a collection or array.
	 * @param index The index. The index does not have to be numerical. <code>value</code> could also be a map in which
	 * case the index could also be a string key.
	 *
	 * @return The indexed value or <code>null</code> if <code>value</code> is <code>null</code> or not a collection or array.
	 *         <code>null</code> is also returned in case the index does not exist.
	 */
	public static Object getIndexedValue(Object value, Integer index) {
		if ( value == null ) {
			return null;
		}

		Iterator<?> iter;
		//Type type = value.getClass();
		Type type = TypeOracle.Instance.getClassType(value.getClass());
		if ( isIterable( type ) ) {
			iter = ( ( Iterable<?> ) value ).iterator();
		}
		else if ( TypeUtils.isArray( type ) ) {
			List<?> arrayList = Arrays.asList( ( Object ) value );
			iter = arrayList.iterator();
		}
		else {
			return null;
		}

		int i = 0;
		Object o;
		while ( iter.hasNext() ) {
			o = iter.next();
			if ( i == index ) {
				return o;
			}
			i++;
		}
		return null;
	}


	/**
	 * Process bean properties getter by applying the JavaBean naming conventions.
	 * 
	 * @param member
	 *          the member for which to get the property name. it should only
	 *          Field or Method
	 * 
	 * @return The bean method name with the "is" or "get" prefix stripped off,
	 *         <code>null</code> the method name id not according to the JavaBeans
	 *         standard.
	 */
	public static String getPropertyName(Object member) {
		String name = null;

		if (member instanceof Field) {
			name = ((Field) member).getName();
		}

		if (member instanceof Method) {
			String methodName = ((Method) member).getName();
			if (methodName.startsWith("is")) {
				name = Introspector.decapitalize(methodName.substring(2));
			} else if (methodName.startsWith("has")) {
				name = Introspector.decapitalize(methodName.substring(3));
			} else if (methodName.startsWith("get")) {
				name = Introspector.decapitalize(methodName.substring(3));
			}
		}
		return name;
	}

	/**
	 * Returns the type of the field of return type of a method.
	 * 
	 * @param member
	 *          the member for which to get the type.
	 * 
	 * @return Returns the type of the field of return type of a method.
	 */
	public static Class<?> getType(Object member) {

		Type type = null;
		if (member instanceof Field) {
			type = ((Field) member).getType();
		}

		if (member instanceof Method) {
			type = ((Method) member).getReturnType();
		}

		if (type.isClassOrInterface() != null)
			return type.isClassOrInterface().getDeclaringClass();
		else
			return null;
	}
	
	
	public static Object getValue(Object member, Object object) {
		Object value = null;

		if ( member instanceof Method ) {
			Method method = ( Method ) member;

				value = method.invoke( object );

			
		}
		else if ( member instanceof Field ) {
			Field field = ( Field ) member;

				value = field.getFieldValue( object );


		}
		return value;
	}

	/**
	 * @param member The <code>Member</code> instance for which to retrieve the type.
	 *
	 * @return Returns the <code>Type</code> of the given <code>Field</code> or <code>Method</code>.
	 *
	 * @throws IllegalArgumentException in case <code>member</code> is not a <code>Field</code> or <code>Method</code>.
	 */
	public static Type typeOf(Object member) {
		if ( member instanceof Field ) {
			return ( ( Field ) member ).getType();
		}
		if ( member instanceof Method ) {
			return ( ( Method ) member ).getReturnType();
		}
		throw new IllegalArgumentException( "Member " + member + " is neither a field nor a method" );
	}
	
	
	
	/**
	 * Returns the autoboxed type of a primitive type.
	 *
	 * @param primitiveType the primitive type
	 *
	 * @return the autoboxed type of a primitive type.
	 *
	 * @throws IllegalArgumentException in case the parameter {@code primitiveType} does not represent a primitive type.
	 */
	public static Class<?> boxedType(Type primitiveType) {
		if ( primitiveType.isPrimitive() == null ) {
			throw new IllegalArgumentException( primitiveType.getClass() + "has to be a primitive type" );
		}

		PrimitiveType type = primitiveType.isPrimitive();
		if ( PrimitiveType.BOOLEAN == type ) {
			return Boolean.class;
		}
		else if ( PrimitiveType.CHAR == type ) {
			return Character.class;
		}
		else if ( PrimitiveType.DOUBLE == type ) {
			return Double.class;
		}
		else if ( PrimitiveType.FLOAT == type ) {
			return Float.class;
		}
		else if ( PrimitiveType.LONG == type ) {
			return Long.class;
		}
		else if ( PrimitiveType.INT == type ) {
			return Integer.class;
		}
		else if ( PrimitiveType.SHORT == type ) {
			return Short.class;
		}
		else if ( PrimitiveType.BYTE == type ) {
			return Byte.class;
		}
		else {
			throw new RuntimeException( "Unhandled primitive type." );
		}
	}

	
	
	private static class Introspector {
		/**
		 * Utility method to take a string and convert it to normal Java variable
		 * name capitalization. This normally means converting the first character
		 * from upper case to lower case, but in the (unusual) special case when
		 * there is more than one character and both the first and second characters
		 * are upper case, we leave it alone.
		 * <p>
		 * Thus "FooBah" becomes "fooBah" and "X" becomes "x", but "URL" stays as
		 * "URL".
		 * 
		 * @param name
		 *          The string to be decapitalized.
		 * @return The decapitalized version of the string.
		 */
		public static String decapitalize(String name) {
			if (name == null || name.length() == 0) {
				return name;
			}
			if (name.length() > 1 && Character.isUpperCase(name.charAt(1))
					&& Character.isUpperCase(name.charAt(0))) {
				return name;
			}
			char chars[] = name.toCharArray();
			chars[0] = Character.toLowerCase(chars[0]);
			return new String(chars);
		}
	}
	
	
	
	/**
	 * Checks whether the specified {@code clazz} extends or inherits the specified super class or interface.
	 *
	 * @param clazz @{code Class} to check.
	 * @param superClassOrInterface The super class/interface {@code clazz}.
	 *
	 * @return {@code true} if {@code clazz} extends or implements {@code superClassOrInterface}, {@code false} otherwise.
	 */
	private static boolean extendsOrImplements(Class<?> clazz, Class<?> superClassOrInterface) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		computeClassHierarchy( clazz, classes );
		return classes.contains( superClassOrInterface );
	}

}
