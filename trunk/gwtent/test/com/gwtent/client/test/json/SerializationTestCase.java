package com.gwtent.client.test.json;

import java.util.ArrayList;
import java.util.List;


import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.serialization.json.JsonSerializer;
import com.gwtent.client.test.GwtEntTestCase;

public class SerializationTestCase extends GwtEntTestCase{
	public void testJson(){
		Person p = new Person();
		p.setName("A");
		p.setAge(50);
		//p.setSex(Sex.FEMALE);
		JsonSerializer serializer = new JsonSerializer();
		String jsonPerson = serializer.serializeObject(p);
		System.out.println(jsonPerson);
		
		serializer = new JsonSerializer();
		Object a = new Double(50);

//		p.setAge(((((Integer)a).intValue())));
		Person p1 = serializer.deserializeObject(jsonPerson, Person.class, null);
		assertTrue(p1.getName().equals(p.getName()));
		assertTrue(p1.getAge() == p.getAge());
		
		
		People people = new People();
		people.add(p);
		serializer = new JsonSerializer();
		String json = serializer.serializeObject(people);
		System.out.println(json);
		
		serializer = new JsonSerializer();
		People p2 = serializer.deserializeObject(json, People.class, new ObjectFactory<Object>(){

			public Object getObject() {
				return new Person();
			}});
		assertTrue(p2.size() == 1);
		assertTrue(p2.get(0).getName().equals(p.getName()));
		assertTrue(p2.get(0).getAge() == p.getAge());
	}
}
