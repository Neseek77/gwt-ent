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
package com.gwtent.client.ui;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.HasMetaData;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.ui.transition.TransitionException;

public class ClassTypeHelper {
	public final static String CLASS_LIST_METADATA = "List";
	public final static String CLASS_CAPTION_METADATA = "Caption";
	public final static String FIELD_CPATION_METADATA = "Caption";
	public final static String FIELD_DESC_METADATA = "Desc";
	public final static String FIELD_VALIDATE_METADATA = "Validate";
	
	public static String getFirstMetaData(HasMetaData metaData, String tagName){
		String[][] meta = metaData.getMetaData(tagName);
		if ((meta.length > 0) && (meta[0].length > 0)){
			return meta[0][0];
		}
		return "";
	}
	
	public static String getAllMetaData(HasMetaData metaData, String tagName){
		StringBuffer sb = new StringBuffer();

		String[][] meta = metaData.getMetaData(tagName);
		for (int i = 0; i < meta.length; i++){
			for (int j = 0; j < meta[i].length; j++){
				if (sb.length() > 0)
					sb.append(" ");
				sb.append(meta[i][j]);
			}
		}
		
		return sb.toString();
	} 
	
	public static String getCaption(ClassType classType, Object instance,
			HasMetaData metaData){
		Object obj = getValue(classType,instance, metaData, FIELD_CPATION_METADATA);
		if (obj != null)
			return (String)obj;
		else
			return "";
	}
	
	public static String getDesc(ClassType classType, Object instance,
			HasMetaData metaData){
		Object obj = getValue(classType,instance, metaData, FIELD_DESC_METADATA);
		if (obj != null)
			return (String)obj;
		else
			return "";
	}
	
	/**
	 * if metaDataName's value have inverted comma, that mean direct 
	 * use the string, other call the method
	 * 
	 * @param classType
	 * @param metaData
	 * @param metaDataName
	 * @return
	 */
	public static Object getValue(ClassType classType, Object instance,
			HasMetaData metaData, String tagName){
		String metaStr = getAllMetaData(metaData, tagName);
		if (metaStr.trim().length() <= 0) return null;
		if (Utils.testIncludeInInvertedComma(metaStr)){
			return Utils.excludeInvertedComma(metaStr);
		}else{
			Method getter = classType.findMethod(metaStr, new String[]{});
			if (getter != null){
				return classType.invoke(instance, metaStr, new Object[]{});
			}else{
				throw new TransitionException("method: " + metaStr + "() not found in class: " + classType.getName() + ".");
			}
		}
	}
	
	
	public static String[] getValidateNames(HasMetaData metaData){
		String str = getAllMetaData(metaData, FIELD_VALIDATE_METADATA);
		return str.split(" ");
	}
	
	
	public static String getGetterName(String field){
		if (field.length() > 0){
			char ch = field.charAt(0);
			return "get" + Character.toUpperCase(ch) + field.substring(1, field.length());
		}else{
			return "";
		}
		
	}
	
	public static String getSetterName(String field){
		if (field.length() > 0){
			char ch = field.charAt(0);
			return "set" + Character.toUpperCase(ch) + field.substring(1, field.length());
		}else{
			return "";
		}
		
	}
	
	
	public static Method findSyncValidateMethod(ClassType classType, String functionName){
		return classType.findMethod(functionName, new String[]{"java.lang.Object"});
	}
	
	public static Method findAsyncValidateMethod(ClassType classType, String functionName){
		return classType.findMethod(functionName, new String[]{"java.lang.Object", "com.coceler.gwt.client.ui.validate.ValidateCallBack"});
	}

}
