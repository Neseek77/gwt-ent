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
package com.gwtent.reflection;

import com.google.gwt.core.ext.typeinfo.JParameter;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.Parameter;


public class Proxy extends ClassType {
	

	public Proxy(){
		addFields();
		addMethods();
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
		Field field = new Field(this, "name");
		//field.addModifierBits(0);   
		//field.addMetaData(tagName, values);  //for... every meta
		field.addMetaData("tag", new String[]{"meta1", "meta2"});
		field.setTypeName("typeName");
		addField(field);
	}
	
	protected void addMethods(){
		//for...
		Method method = new Method(this, "name");
		//method.addModifierBits(bits);
		//method.addMetaData(tagName, values)
		method.setReturnTypeName("returnTypeName");
		//JParameter jparam = null;
		//new Parameter(method, jparam.getType().getQualifiedSourceName(), jparam.getName());
	}
	
	


}
