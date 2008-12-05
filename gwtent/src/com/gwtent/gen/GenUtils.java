/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


package com.gwtent.gen;

import java.lang.reflect.Method;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.gwtent.client.CheckedExceptionWrapper;

public class GenUtils {
	
	public static String getReflection_SUFFIX(){
		return "_Visitor";
	}
	
	public static String getAOP_SUFFIX(){
		return "__AOP";
	}

	public static Class<?> gwtTypeToJavaClass(JType type){
		try {
			return Class.forName(type.getJNISignature().substring(1, type.getJNISignature().length() - 1).replace('/', '.'));
		} catch (ClassNotFoundException e) {
			throw new CheckedExceptionWrapper("Cann't get class from gwt JClassType." + e.getMessage(), e);
		}
	}
	
	public static Method gwtMethodToJavaMethod(JMethod method){
		Class<?> clazz = gwtTypeToJavaClass(method.getEnclosingType());
		Class<?>[] paramClasses = new Class<?>[method.getParameters().length];
		JParameter[] params = method.getParameters();
		for (int i = 0; i < params.length; i++) {
			paramClasses[i] = gwtTypeToJavaClass(params[i].getType());
		}
		try {
			return clazz.getMethod(method.getName(), paramClasses);
		} catch (SecurityException e) {
			throw new CheckedExceptionWrapper(e);
		} catch (NoSuchMethodException e) {
			throw new CheckedExceptionWrapper("NoSuchMethod? GWT Method: " + method.toString() + " EnclosingType: " + method.getEnclosingType().toString(), e);
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
	
	public static boolean hasPublicDefaultConstructor(JClassType classType){
		for (JConstructor constructor : classType.getConstructors()){
			if ((constructor.getParameters().length == 0) && constructor.isPublic())
				return true;
		}
		
		return false;
	}
	
	public static boolean checkIfReturnVoid(JMethod method){
		return method.getReturnType().getSimpleSourceName().equals("void");
	}
	
}
