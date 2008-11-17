package com.gwtent.gen;

import java.lang.reflect.Method;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.gwtent.client.CheckedExceptionWrapper;

public class GenUtils {

	public static Class<?> GWTTypeToClass(JType type){
		try {
			return Class.forName(type.getJNISignature().substring(1, type.getJNISignature().length() - 1).replace('/', '.'));
		} catch (ClassNotFoundException e) {
			throw new CheckedExceptionWrapper("Cann't get class from gwt JClassType." + e.getMessage(), e);
		}
	}
	
	public static Method JMethodToMethod(JMethod method){
		Class<?> clazz = GWTTypeToClass(method.getEnclosingType());
		Class<?>[] paramClasses = new Class<?>[method.getParameters().length];
		JParameter[] params = method.getParameters();
		for (int i = 0; i < params.length; i++) {
			paramClasses[i] = GWTTypeToClass(params[i].getType());
		}
		try {
			return clazz.getMethod(method.getName(), paramClasses);
		} catch (SecurityException e) {
			throw new CheckedExceptionWrapper(e);
		} catch (NoSuchMethodException e) {
			throw new CheckedExceptionWrapper(e);
		}
	}
	
	public static String getParamTypeNames(JMethod method, char quotationMark){
		StringBuilder result = new StringBuilder("");
		boolean needComma = false;
		for (JParameter param : method.getParameters()){
			if (needComma)
				result.append(',').append(quotationMark + param.getType().getQualifiedSourceName() + quotationMark);
			else{
				result.append(quotationMark + param.getType().getQualifiedSourceName() + quotationMark);
				needComma = true;
			}
		}
		
		return result.toString();
	}
	
	public static String getParamNames(JMethod method){
		StringBuilder result = new StringBuilder("");
		boolean needComma = false;
		for (JParameter param : method.getParameters()){
			if (needComma)
				result.append(',').append(param.getName());
			else{
				result.append(param.getName());
				needComma = true;
			}
		}
		
		return result.toString();
	}
	
	public static boolean hasDefaultConstructor(JClassType classType){
		for (JConstructor constructor : classType.getConstructors()){
			if (constructor.getParameters().length == 0)
				return true;
		}
		
		return false;
	}
	
}
