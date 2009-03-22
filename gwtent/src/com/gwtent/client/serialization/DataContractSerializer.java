package com.gwtent.client.serialization;

import com.gwtent.client.reflection.ClassType;

public interface DataContractSerializer {
	public Object deserializeObject(String json, ClassType type);
	public String serializeObject(Object object);
}
