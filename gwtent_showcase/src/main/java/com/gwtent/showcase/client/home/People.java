package com.gwtent.showcase.client.home;

import java.util.ArrayList;

import com.gwtent.serialization.client.DataContract;
import com.gwtent.serialization.client.DataMember;

@DataContract(type=Person.class)
public class People extends ArrayList<Person> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//TODO Support member under an array element?
	@DataMember
	private String desc;

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
