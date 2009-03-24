package com.gwtent.client.serialization;

import com.gwtent.client.reflection.ClassType;

public interface DataContractSerializer {
	public Object deserializeObject(String json, Class<?> clazz);
	public String serializeObject(Object object);
}
