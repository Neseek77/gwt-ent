package com.gwtent.validate.client.util;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Type;
import com.gwtent.reflection.client.TypeOracle;

/**
 * 
 * @author James Luo
 *
 * 10/08/2010 12:28:23 PM
 */
public class TypeUtils {
	
	public static boolean isAssignable(Type supertype, Type type){
		//return true;
		if (type.isParameterized() != null)
			type = type.isParameterized().getRawType();
		
		if (type.isPrimitive() != null)
			type = TypeOracle.Instance.getType(type.isPrimitive().getQualifiedBoxedSourceName());
		
		if (type.isClassOrInterface() != null && supertype.isClassOrInterface() != null)
			return ReflectionHelper.extendsOrImplements(type.isClassOrInterface().getDeclaringClass(), type.isClassOrInterface().getDeclaringClass());
		
		return false;
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
		return (type instanceof ClassType && (type.isArray() != null));
			//|| (type instanceof GenericArrayType);
	}
}
