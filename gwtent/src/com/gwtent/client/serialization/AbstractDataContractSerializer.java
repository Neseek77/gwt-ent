package com.gwtent.client.serialization;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;

public abstract class AbstractDataContractSerializer implements DataContractSerializer {

	public <T extends Object> T deserializeObject(String json, Class<T> clazz, ObjectFactory<Object> objectFactory) {
		ClassType type = TypeOracle.Instance.getClassType(clazz);
		return (T) deserializeObject(json, type, objectFactory);
	}
	
	protected abstract Object deserializeObject(String json, ClassType type, ObjectFactory<Object> objectFactory);
	protected abstract String serializeObject(Object object, ClassType type);

	public String serializeObject(Object object) {
	  ClassType type = TypeOracle.Instance.getClassType(object.getClass());
	  return serializeObject(object, type);
	}

}
