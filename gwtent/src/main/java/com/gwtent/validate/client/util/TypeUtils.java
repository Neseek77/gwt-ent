package com.gwtent.validate.client.util;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Type;

/**
 * 
 * @author James Luo
 *
 * 10/08/2010 12:28:23 PM
 */
public class TypeUtils {
	
	public static boolean isAssignable(Type supertype, Type type){
		return true;
	}
	
	
	public static Type getArrayType(Type componentType){
		return null;
	}
	
	public static Type getComponentType(Type type){
		if (type.isArray() != null)
			return type.isArray().getComponentType();
			
		return null;
	}
	
	public static boolean isArray(Type type)
	{
		return (type instanceof ClassType && ((ClassType) type).isArray() != null);
			//|| (type instanceof GenericArrayType);
	}
}
