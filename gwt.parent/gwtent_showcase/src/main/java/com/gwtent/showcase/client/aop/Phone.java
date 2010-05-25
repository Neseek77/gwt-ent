package com.gwtent.showcase.client.aop;

import java.util.HashMap;
import java.util.Map;

import com.gwtent.aop.client.Aspectable;
import com.gwtent.showcase.client.aop.Interceptors.AOPTestAnnotation;


public class Phone implements Aspectable {
	public static final Map<Number, Receiver> RECEIVERS = new HashMap<Number, Receiver>();

	static {
		RECEIVERS.put(0, new Receiver("The President"));
		RECEIVERS.put(1234567, new Receiver("Aunt Jane"));
		RECEIVERS.put(1111111, new Receiver("Santa"));
		RECEIVERS.put(1101010, new Receiver("Osama bin Laden"));
	}
	
	/**
	 * the phone number, like your home number
	 */
	private Number number;

	@AOPTestAnnotation(value="abc")
	public Receiver call(Number number) {
		System.out.println("The call here...");
		Receiver result = RECEIVERS.get(number);
		if (result != null)
			return result;
		else
			throw new NumberNotFoundException("Can't  found receiver, number: " + number);
	}
	
	public Receiver call(String number){
		System.out.println("The call here...");
		return RECEIVERS.get(111111111);
	}
	
	public String toString(){
		return super.toString();
	}
	

	public void setNumber(Number number) {
		this.number = number;
	}

	public Number getNumber() {
		return number;
	}
	
	public static class NumberNotFoundException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public NumberNotFoundException(String msg){
			super(msg);
		}
		
	}


	public static class Receiver {
		private final String name;

		public Receiver(String name) {
			this.name = name;
		}
		
		public String getName(){
			return name;
		}

		@Override
		public String toString() {
			return getClass().getName() + "[name=" + name + "]";
		}
	}
}


