package com.gwtent.client.test.json;

import java.util.ArrayList;

import com.gwtent.client.serialization.DataContract;
import com.gwtent.client.serialization.DataMember;

@DataContract(type=Person.class)
public class People extends ArrayList<Person> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DataMember
	private String desc;

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
