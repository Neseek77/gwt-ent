package com.gwtent.client.serialization.json;

import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.reflection.AnnotationStore;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Constructor;
import com.gwtent.client.reflection.TypeOracle;

public class ObjectReflectionFactory implements ObjectFactory<Object> {

	private Constructor constructor;
	
	public ObjectReflectionFactory(ClassType type){
		setType(type);
	}
	
	/**
	 * The 
	 * @param annotation @DataContract and @DataMember
	 */
	public ObjectReflectionFactory(AnnotationStore annotation){
		ClassType type = null;
		if (annotation.getValue("className") != null && annotation.getValue("className").length() > 0){
			type = TypeOracle.Instance.getClassType(annotation.getValue("className"));
		}else if (annotation.getValue("clazz").length() > 0){
			String typeName = annotation.getValue("clazz");
			if (! typeName.equals("java.lang.Object")){
				type = annotation.getAsClassType("clazz");
			}
		}
		if (type != null)
			setType(type);
		else
			throw new RuntimeException("Can not found ClassType from annotation(DataContract or DataMember request \"clazz\" value when annotation a Collection(ie List)");
	}
	
	private void setType(ClassType type){
		constructor = type.findConstructor(new String[0]);
	}
	
	public Object getObject() {
		return constructor.newInstance();
	}

}
