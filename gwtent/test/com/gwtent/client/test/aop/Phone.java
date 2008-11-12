package com.gwtent.client.test.aop;

import java.util.HashMap;
import java.util.Map;

import com.gwtent.client.aop.Aspectable;


public class Phone implements Aspectable {
	private static final Map<Number, Receiver> RECEIVERS = new HashMap<Number, Receiver>();

	static {
		RECEIVERS.put(123456789, new Receiver("Aunt Jane"));
		RECEIVERS.put(111111111, new Receiver("Santa"));
	}

	public Receiver call(Number number) {
		System.out.println("The call here...");
		return RECEIVERS.get(number);
	}
	
	public String toString(){
		return super.toString();
	}
	
	public static class Receiver {
		private final String name;

		public Receiver(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return getClass().getName() + "[name=" + name + "]";
		}
	}
}


