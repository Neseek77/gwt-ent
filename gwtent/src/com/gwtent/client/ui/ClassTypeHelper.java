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


package com.gwtent.client.ui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.gwtent.client.CheckedExceptionWrapper;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.HasMetaData;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.impl.ClassTypeImpl;
import com.gwtent.client.ui.transition.TransitionException;

public class ClassTypeHelper {
	public final static String CLASS_FIELD_LIST_METADATA = "Fields";
	public final static String CLASS_ACTION_LIST_METADATA = "Actions";
	
	public final static String CLASS_CAPTION_METADATA = "Caption";
	
	public final static String FIELD_FLAG_METADATA = "Field";
	public final static String FIELD_CPATION_METADATA = "Caption";
	public final static String FIELD_DESC_METADATA = "Desc";
	public final static String FIELD_VALIDATE_METADATA = "Validate";
	
	public final static String ACTION_FLAG_METADATA = "Action";
	public final static String ACTION_CPATION_METADATA = "Caption";
	
	public final static String OBJECT_CLASS_NAME = "java.lang.Object";
	
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
	
	public static String getCaption(ClassTypeImpl classType, Object instance,
			HasMetaData metaData){
		Object obj = getValue(classType,instance, metaData, FIELD_CPATION_METADATA);
		if (obj != null)
			return (String)obj;
		else
			return "";
	}
	
	public static String getDesc(ClassTypeImpl classType, Object instance,
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
	public static Object getValue(ClassTypeImpl classType, Object instance,
			HasMetaData metaData, String tagName){
		String metaStr = getAllMetaData(metaData, tagName);
		if (metaStr.trim().length() <= 0) return null;
		if (Utils.testIncludeInInvertedComma(metaStr)){
			return Utils.excludeInvertedComma(metaStr);
		}else{
			Method getter = classType.findMethod(metaStr, new String[]{});
			if (getter != null){
				try {
					return classType.invoke(instance, metaStr, new Object[]{});
				} catch (Exception e) {
					throw new CheckedExceptionWrapper(e);
				}
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
	
	
	public static boolean queryHaveMetaData(HasMetaData metaData, String tagName){
		Set tags = new HashSet();
		tags.add(tagName);
		
		return queryHaveMetaData(metaData, tags);
	}
	
	public static boolean queryHaveMetaData(HasMetaData metaData, Set tags){
		Iterator iterator = tags.iterator();
		while (iterator.hasNext()){
			if (metaData.getMetaData((String)iterator.next()).length > 0){
				return true;
			}
		}
		
		return false;
	}

}
