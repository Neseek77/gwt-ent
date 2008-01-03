package com.gwtent.client.ui.model.value;

import java.util.HashMap;
import java.util.Map;

public class ValueRegister {
	private Map map = new HashMap();
	
	public void register(String typeName, ValueCreator valueCreator){
		map.put(typeName, valueCreator);
	}
	
	public ValueCreator getValueCreator(String typeName){
		return (ValueCreator)map.get(typeName);
	}
	
	private static ValueRegister valueRegister = null;
	
	public static ValueRegister getInstance(){
		if (valueRegister == null)
			valueRegister = new ValueRegister();
		
		return valueRegister;
	}
}
