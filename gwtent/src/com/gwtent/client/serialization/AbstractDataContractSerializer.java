package com.gwtent.client.serialization;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;

public abstract class AbstractDataContractSerializer implements DataContractSerializer {

	public Object deserializeObject(String json, ClassType type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected abstract String serializeObject(Object object, ClassType type);

	public String serializeObject(Object object) {
	  ClassType type = TypeOracle.Instance.getClassType(object.getClass());
	  return serializeObject(object, type);
	}

}
