package com.gwtent.client.serialization;

import com.gwtent.client.reflection.ClassType;

public interface DataContractSerializer {
	public <T extends Object> T deserializeObject(String json, Class<T> clazz, ObjectFactory<Object> objectFactory);
	public String serializeObject(Object object);
}
