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
package com.gwtent.gen.reflection;

import com.google.gwt.core.ext.typeinfo.JAnnotationMethod;
import com.google.gwt.core.ext.typeinfo.JAnnotationType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.client.reflection.AccessDef;
import com.gwtent.client.reflection.AnnotationStore;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.gen.reflection.accessadapter.JFeildAdapter;
import com.gwtent.gen.reflection.accessadapter.JMethodAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorHelper {
	public static int AccessDefToInt(AccessDef accessDef){
		int result = 0;
		
		if (accessDef.isFinal()) result += TypeOracle.MOD_FINAL;
		if (accessDef.isPrivate()) result += TypeOracle.MOD_PRIVATE;
		if (accessDef.isProtected()) result += TypeOracle.MOD_PROTECTED;
		if (accessDef.isPublic()) result += TypeOracle.MOD_PUBLIC;
		if (accessDef.isStatic()) result += TypeOracle.MOD_STATIC;
		
		return result;
	}
	
	public static int AccessDefToInt(JField field){
		JFeildAdapter adapter = new JFeildAdapter(field);
		return AccessDefToInt(adapter);
	}
	
	public static int AccessDefToInt(JMethod method){
		JMethodAdapter adapter = new JMethodAdapter(method);
		return AccessDefToInt(adapter);
	}
	
	
	
	public static String stringArrayToCode(String[] strs){
		StringBuilder result = new StringBuilder("new String[]{");
		
		for (int i = 0; i < strs.length; i++){
			result.append("\"" + processInvertedComma(strs[i]) + "\"");
			
			if (i < (strs.length - 1)) result.append(", ");
		}
		
		result.append("}");
		
		return result.toString();
	}
	
	public static String getUnitName(JClassType classType){
		return classType.getParameterizedQualifiedSourceName()
			+ "Wrapper";
	}
	
	public static String getSimpleUnitName(JClassType classType){
		return classType.getSimpleSourceName() + "Wrapper";
	}
	
	/**
	 * generator metaData
	 * @param dest field or method or class
	 * @param source source to print code
	 * @param metaData 
	 */
	public static void addMetaDatas(String dest, SourceWriter source, com.google.gwt.core.ext.typeinfo.HasMetaData metaData) {
		String[] tags = metaData.getMetaDataTags();
		for (int j = 0; j < tags.length; j++){
			String[][] metas = metaData.getMetaData(tags[j]);
			for (int k = 0; k < metas.length; k++){
				source.println(dest + ".addMetaData(\"" + tags[j] + "\", " + GeneratorHelper.stringArrayToCode(metas[k]) +  ");");
			}
		}
	}
	
	public static String processInvertedComma(String str){
//		return str.replaceAll("\"", "\\\"");
		StringBuffer sb = new StringBuffer();
		int length = str.length();
		for (int i = 0; i < length; i++){
			//not first char and last char  (i != 0) && (i != (length - 1)) && 
			if ((str.charAt(i) == '"')){
				sb.append("\\\"");
			}else{
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
		
	}
	
	/**
	 * 
	 * @param typeOracle
	 * @param mapValueName
	 * @param source
	 * @param annotation
	 * 
	 * Must declare values first 
	 * 
	 * Map<String, Object> values = new HashMap<String, Object>();
	 * values.clear();
	 * values.put("name", "Test_Table");
	 * values.put("name1", "value1");
	 * AnnotationStoreImpl store = new AnnotationStoreImpl(com.gwtent.test.TestAnnotation.class, values);
	 */
	public static void addAnnotation(com.google.gwt.core.ext.typeinfo.TypeOracle typeOracle, 
	    String AnnotationStoreVarName, SourceWriter source, Annotation annotation){
	  source.println("{");
	  source.println("Map<String, Object> values = new HashMap<String, Object>();");
	  String mapVarName = "values";
	  //source.println(mapVarName + ".clear();");
	  try {
      JClassType classType = typeOracle.getType(annotation.annotationType().getName());
      JAnnotationType annoType = classType.isAnnotation();
      JAnnotationMethod[] methods = annoType.getMethods();
      for (JAnnotationMethod method : methods) {
        Object value = null;
        try {
          //Currently just support mothod without parameters
          value = annotation.annotationType().getMethod(method.getName(), new Class[]{}).invoke(annotation, null);
          source.println(mapVarName + ".put(\"" + method.getName() + "\", \"" + value.toString() + "\");");
        } catch (IllegalArgumentException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (SecurityException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (NoSuchMethodException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    } catch (NotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    source.println(AnnotationStoreVarName + " = new AnnotationStoreImpl(" + annotation.annotationType().getName() + ".class, " + mapVarName + ");");
    source.println("}");
	}
	
	/**
	 * 
	 * @param dest
	 * @param source
	 * @param annotations
	 * 
	 * {@code}
	 * List<AnnotationStore> list = new ArrayList<AnnotationStore>();
	 * AnnotationStoreImpl store = null;
	 * 
	 * addAnnotation(values, store); //this will create AnnotationStoreImpl
	 * list.add(store);
	 * ...repeat last two lines
	 * xx.addAnnotations(list);
	 * 
	 */
	public static void addAnnotations(com.google.gwt.core.ext.typeinfo.TypeOracle typeOracle,
	    String dest, SourceWriter source, Annotation[] annotations){
		
	  if (annotations.length <= 0)
		  return;
		
	  source.indent();
	  source.println("{");
	  source.println("List<AnnotationStore> list = new ArrayList<AnnotationStore>();");
	  source.println("AnnotationStoreImpl store = null;");
	  
	  for (Annotation annotation : annotations) {
	    addAnnotation(typeOracle, "store", source, annotation);
	    source.println("list.add(store);");
    }
	  
	  source.println(dest + ".addAnnotations(list);");
	  source.println("}");
	  source.outdent();
	}
	
	
	
}
