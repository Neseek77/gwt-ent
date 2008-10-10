package com.gwtent.client.test.aop;

import com.gwtent.client.aop.Aspectable;

public class TestAOP implements Aspectable{
	public String sayHello(String guestName){
		return "Hello: " + guestName;
	}
}
