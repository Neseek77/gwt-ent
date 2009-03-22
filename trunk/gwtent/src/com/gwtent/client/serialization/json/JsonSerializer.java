package com.gwtent.client.serialization.json;

import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.serialization.AbstractDataContractSerializer;
import com.gwtent.client.serialization.DataMember;

public class JsonSerializer extends AbstractDataContractSerializer{
	public Object deserializeObject(String json, ClassType type){
		JSONValue value = JSONParser.parse(json);
		return value;
	}
	
	public String serializeObject(Object object, ClassType type){
		StringBuilder sb = new StringBuilder();
		
		if (object instanceof Map){
			
		}else	if (object instanceof Iterable){
			
		} else {
			sb.append(serializePureObject(object, type).toString());
		}
		
		return sb.toString();
	}
	
	private JSONValue serializePureObject(Object object, ClassType type){
		JSONObject result = new JSONObject();
		
		for (Field field : ReflectionUtils.getAllFields(type, DataMember.class)){
			Object value = field.getFieldValue(object);
			
			if (value == null){
				result.put(field.getName(), JSONNull.getInstance());
			}else if (value instanceof Boolean){
				result.put(field.getName(), JSONBoolean.getInstance((Boolean)value));
			} else if (value instanceof Number){
				result.put(field.getName(), new JSONNumber(((Number)value).doubleValue()));
			} else{
				result.put(field.getName(), new JSONString(value.toString()));
			}
		}
		
		return result;
	}
}
