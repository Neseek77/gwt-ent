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
package com.gwtent.client.ui.transition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.HasMetaData;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.Reflection;
import com.gwtent.client.reflection.Type;
import com.gwtent.client.ui.ClassTypeHelper;
import com.gwtent.client.ui.Utils;
import com.gwtent.client.ui.model.Fields;
import com.gwtent.client.ui.model.Value;
import com.gwtent.client.ui.model.impl.FieldImpl;
import com.gwtent.client.ui.model.impl.FieldsImpl;
import com.gwtent.client.ui.model.value.ValueFactory;

/**
 * Improve reflection support to pojo
 * 
 * 1, Class level
 *
 * 
 * @author James Luo
 * 2007-12-27 下午08:50:52
 *
 */
public class ReflectionToModel implements POJOToModel {
	
	private ValueFactory valueFactory;
	private Object pojo = null;
	
	public ReflectionToModel(ValueFactory valueFactory){
		this.valueFactory = valueFactory;
	}

	public ValueFactory getValueFactory() {
		return valueFactory;
	}


	public void setValueFactory(ValueFactory valueFactory) {
		this.valueFactory = valueFactory;
	}


	public Fields createModel(Object pojo, ClassType classType) throws TransitionException {
		this.pojo = pojo;
		
		if (pojo instanceof Reflection){
			//pojo.getClass()
			//ClassType classType = (ClassType)GWT.create(clasz);
			Fields fields = new FieldsImpl();
			fields.setCaption(ClassTypeHelper.getAllMetaData(classType, ClassTypeHelper.CLASS_CAPTION_METADATA));
			addFields(fields, classType);
			return fields;
		}else{
			throw new TransitionException("pojo must reflectable when use ReflectionToModel");
		}
	}
	
	
	protected void addFields(Fields fields, ClassType classType){
		List fieldNames = new ArrayList();
		Utils.addListByStrIFNotExists(getFieldNamesByClassMetaData(classType), fieldNames);
		Utils.addListByStrIFNotExists(getFieldNamesByFieldMetaData(classType), fieldNames);
		
		Iterator iterator = fieldNames.iterator();
		while (iterator.hasNext()){
			String fieldName = (String)iterator.next();
			addField(fields, classType, fieldName);
		}
	}
	
	protected com.gwtent.client.ui.model.Field addField(Fields fields, ClassType classType, String fieldName){
		HasMetaData metaData = null;
		String typeName = "";
		Field srcField = classType.findField(fieldName);
		if (srcField != null){
			metaData = srcField;
			typeName = srcField.getTypeName();
		}else{  
			Method srcMethod = classType.findMethod(fieldName, new Type[]{});
			if (srcMethod != null){
				metaData = srcMethod;
				typeName = srcMethod.getReturnTypeName();
			}
		}
		
		if (metaData != null){
			Value value = valueFactory.factory(pojo, classType, fieldName, typeName);
			if (value == null){
				throw new TransitionException("value factory return null, is type:" + typeName + "have correct value type.");
			}
			com.gwtent.client.ui.model.Field destField = new FieldImpl();
			updateFieldByMetaData(destField, classType, metaData, fieldName);
			//TODO destField.setRequire(false); set require by validate
			destField.setRequire(false);
			destField.setValue(value);
			fields.addField(destField);
			return destField;
		}else{
			return null;
		}
	}
	
	protected void updateFieldByMetaData(com.gwtent.client.ui.model.Field field, ClassType classType, HasMetaData metaData, String name){
		String caption = ClassTypeHelper.getCaption(classType, pojo, metaData);
		if (caption.length() <= 0) caption = name;
		field.setCaption(caption);
		
		String desc = ClassTypeHelper.getDesc(classType, pojo, metaData);
		field.setDesc(desc);
	}


	private List getFieldNamesByClassMetaData(ClassType classType) {
		List result = new ArrayList();
		Utils.addListByStrIFNotExists(classType.getMetaDataMerge(ClassTypeHelper.CLASS_LIST_METADATA), result);
		return result;
	}
	
	private boolean queryMetaData(HasMetaData metaData){
		Set tags = new HashSet();
		tags.add(ClassTypeHelper.FIELD_CPATION_METADATA);
		tags.add(ClassTypeHelper.FIELD_DESC_METADATA);
		tags.add(ClassTypeHelper.FIELD_VALIDATE_METADATA);
		
		Iterator iterator = tags.iterator();
		while (iterator.hasNext()){
			if (metaData.getMetaData((String)iterator.next()).length > 0){
				return true;
			}
		}
		
		return false;
	}
	
	private List getFieldNamesByFieldMetaData(ClassType classType) {
		List result = new ArrayList();
		Field[] fields = classType.getFields();
		for (int i = 0; i < fields.length; i++){
			Field field = fields[i];
			if (queryMetaData(field)){
				result.add(field.getName());
			} 
		}
		
		Method[] methods = classType.getMethods();
		for (int i = 0; i < methods.length; i++){
			Method method = methods[i];
			if (queryMetaData(method)){
				result.add(method.getName());
			} 
		}
		
		return result;
	}

}
