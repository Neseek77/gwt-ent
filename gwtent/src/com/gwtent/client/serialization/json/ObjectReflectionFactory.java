package com.gwtent.client.serialization.json;

import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.reflection.AnnotationStore;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Constructor;
import com.gwtent.client.reflection.TypeOracle;

public class ObjectReflectionFactory implements ObjectFactory<Object> {

	/**
	 * the name of annotation method name
	 */
	private static final String ANNOTATION_TYPE_NAME = "typeName";
	private static final String ANNOTATION_TYPE = "type";
	
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
		if (annotation.getValue(ANNOTATION_TYPE_NAME) != null && annotation.getValue(ANNOTATION_TYPE_NAME).length() > 0){
			type = TypeOracle.Instance.getClassType(annotation.getValue(ANNOTATION_TYPE_NAME));
		}else if (annotation.getValue(ANNOTATION_TYPE).length() > 0){
			String typeName = annotation.getValue(ANNOTATION_TYPE);
			if (! typeName.equals("java.lang.Object")){
				type = annotation.getAsClassType(ANNOTATION_TYPE);
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
