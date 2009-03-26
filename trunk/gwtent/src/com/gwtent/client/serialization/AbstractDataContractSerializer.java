package com.gwtent.client.serialization;

import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;

public abstract class AbstractDataContractSerializer implements DataContractSerializer {

	public <T extends Object> T deserializeObject(String json, Class<T> clazz, ObjectFactory<Object> objectFactory) {
		ClassType type = TypeOracle.Instance.getClassType(clazz);
		return (T) deserializeObject(json, type, objectFactory, null);
	}
	
	protected abstract Object deserializeObject(String json, ClassType type, ObjectFactory<Object> objectFactory, DoubleConvert doubleConvert);
	protected abstract String serializeObject(Object object, ClassType type);

	public String serializeObject(Object object) {
	  ClassType type = TypeOracle.Instance.getClassType(object.getClass());
	  return serializeObject(object, type);
	}


	public <T> T deserializeObject(String json, Class<T> clazz,
			ObjectFactory<Object> objectFactory, DoubleConvert doubleConvert) {
		ClassType type = TypeOracle.Instance.getClassType(clazz);
		return (T) deserializeObject(json, type, objectFactory, doubleConvert);
	}

}
