package com.gwtent.client.serialization;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;

public abstract class AbstractDataContractSerializer implements DataContractSerializer {

	public Object deserializeObject(String json, Class<?> clazz) {
		ClassType type = TypeOracle.Instance.getClassType(clazz);
		return deserializeObject(json, type);
	}
	
	protected abstract Object deserializeObject(String json, ClassType type);
	protected abstract String serializeObject(Object object, ClassType type);

	public String serializeObject(Object object) {
	  ClassType type = TypeOracle.Instance.getClassType(object.getClass());
	  return serializeObject(object, type);
	}

}
