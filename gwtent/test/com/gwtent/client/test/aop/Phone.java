package com.gwtent.client.test.aop;

import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import com.gwtent.client.aop.Aspectable;


public class Phone implements Aspectable {
	private static final Map<Number, Receiver> RECEIVERS = new HashMap<Number, Receiver>();

	static {
		RECEIVERS.put(123456789, new Receiver("Aunt Jane"));
		RECEIVERS.put(111111111, new Receiver("Santa"));
	}
	
	/**
	 * the phone number, like your home number
	 */
	private Number number;

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

		@Override
		public String toString() {
			return getClass().getName() + "[name=" + name + "]";
		}
	}
}


