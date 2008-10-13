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

import com.google.gwt.core.ext.typeinfo.JParameter;

import com.gwtent.client.reflection.AnnotationStoreImpl;
import com.gwtent.client.reflection.impl.ClassTypeImpl;
import com.gwtent.client.reflection.impl.FieldImpl;
import com.gwtent.client.reflection.impl.MethodImpl;
import com.gwtent.client.reflection.impl.ParameterImpl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;


public class Proxy extends ClassTypeImpl {
	

	public Proxy(){
		super("classname");
		addFields();
		addMethods();
	}

	protected Map<Class<? extends Annotation>, Annotation> AnnotationsArrayToMaps(Annotation[] annotations){
	  Map<Class<? extends Annotation>, Annotation> result = new HashMap<Class<? extends Annotation>, Annotation>();
	  for (Annotation annotation : annotations) {
     result.put(annotation.getClass(), annotation); 
    }
	  return result;
	}
	
	public void addAnnotations(){
	  
	}
	
	protected void checkInvokeParams(String methodName, int paramCount, Object[] args) throws IllegalArgumentException{
		if (args.length != paramCount){
			throw new IllegalArgumentException("Method: " + methodName + "request " + paramCount + " params, but invoke provide " + args.length + " params.");
		} 
	}
	
	
	public Object invoke(Object instance, String methodName, Object[] args) {
		Object content = instance;
		if (methodName.equals("method1")) {
			checkInvokeParams(methodName, 3, args);
			return ((Object)content).toString();
		}else{
			if (methodName.equals("method1")) {
				checkInvokeParams(methodName, 3, args);
				return ((Object)content).toString();
			}else{
				throw new IllegalArgumentException("Method: " + methodName + " can't found.");
			}
		}
	}
	
	
	protected void addFields(){
		//for...
		FieldImpl field = new FieldImpl(this, "name");
		//field.addModifierBits(0);   
		//field.addMetaData(tagName, values);  //for... every meta
		field.addMetaData("tag", new String[]{"meta1", "meta2"});
		field.setTypeName("typeName");
		addField(field);
	}
	
	protected void addMethods(){
		//for...
		MethodImpl method = new MethodImpl(this, "name");
		//method.addModifierBits(bits);
		//method.addMetaData(tagName, values)
		method.setReturnTypeName("returnTypeName");
		//JParameter jparam = null;
		//new Parameter(method, jparam.getType().getQualifiedSourceName(), jparam.getName());
	}
	
	
	protected void addAnnotation(Annotation[] annotations){
	  Map<String, Object> values = new HashMap<String, Object>();
	  values.put("name", "Test_Table");
	  //AnnotationStoreImpl store = new AnnotationStoreImpl(com.gwtent.test.TestAnnotation.class, values);
	}


}
