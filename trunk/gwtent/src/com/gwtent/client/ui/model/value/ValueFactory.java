package com.gwtent.client.ui.model.value;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.ui.model.Value;

public interface ValueFactory {

	public Value factory(Object pojo, ClassType classType, String fieldName, String typeName);

}