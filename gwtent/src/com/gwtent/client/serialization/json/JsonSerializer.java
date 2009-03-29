package com.gwtent.client.serialization.json;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Constructor;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.client.serialization.AbstractDataContractSerializer;
import com.gwtent.client.serialization.DataMember;
import com.gwtent.client.serialization.DoubleConvert;

public class JsonSerializer extends AbstractDataContractSerializer{
	protected Object deserializeObject(String json, ClassType type, 
			ObjectFactory<Object> objectFactory, DoubleConvert doubleConvert){
		JSONValue value = JSONParser.parse(json);
		
		Constructor constructor = type.findConstructor(new String[0]);
		Object result = constructor.newInstance();
		
		deserialize(value, result, type);
		
		return result;
	}

	private void deserialize(JSONValue value, Object result, ClassType type) {
		if (value instanceof JSONArray){
			
			if (result instanceof Collection){
				ObjectFactory objectFactory = new ObjectReflectionFactory(type);
				deserializeArray((JSONArray)value, (Collection)result, objectFactory);
			}else{
				throw new RuntimeException("JSONArray request a Collection object to contain it.");
			}
		}else if (value instanceof JSONObject){
			deserializePureObject((JSONObject)value, result);
		}
	}
	
	protected String serializeObject(Object object, ClassType type){
		StringBuilder sb = new StringBuilder();
		
  	sb.append(serialize(object, type).toString());
		
		return sb.toString();
	}
	
	private JSONValue serialize(Object object, ClassType type){
		if (object instanceof Map){
			return null;
		}else	if (object instanceof Iterable){
			return serializeIterable((Iterable)object);
		} else {
			return serializePureObject(object, type);
		}
	}
	
	private void deserializeArray(JSONArray array, Collection object, ObjectFactory<Object> objectFactory){
		for (int i = 0; i < array.size(); i++){
			Object objectItem = objectFactory.getObject();
			deserializePureObject((JSONObject)(array.get(i)), objectItem);
			object.add(objectItem);
		}
	}
	
	private void deserializePureObject(JSONObject value, Object obj){
		ClassType type = TypeOracle.Instance.getClassType(obj.getClass());
		for (Field field : type.getFields()){
			
			if (value.containsKey(field.getName())){
				JSONValue fieldValue = value.get(field.getName());
					
				if (fieldValue instanceof JSONObject){
					
				}else{
					Object fieldObject = null;
					if (fieldValue instanceof JSONNull)
						fieldObject = null;
					else if (fieldValue instanceof JSONBoolean)
						fieldObject = ((JSONBoolean)fieldValue).booleanValue();
					else if (fieldValue instanceof JSONNumber)
						fieldObject = ((JSONNumber)fieldValue).doubleValue();
					else if (fieldValue instanceof JSONString) 
						fieldObject = ((JSONString)fieldValue).stringValue();
					
					if ((obj instanceof DoubleConvert) && (fieldValue instanceof JSONNumber))
						((DoubleConvert)obj).convertDouble(field.getName(), (Double)fieldObject);
					else
						field.setFieldValue(obj, fieldObject);
				}
			}			
		}
	}
	
	private JSONValue serializeIterable(Iterable objects){
		JSONArray result = new JSONArray();
		int index = 0;
		for (Object obj : objects){
			result.set(index, serialize(obj, TypeOracle.Instance.getClassType(obj.getClass())));
			index++;
		}
		return result;
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
