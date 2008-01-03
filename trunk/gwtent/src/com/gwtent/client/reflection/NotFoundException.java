package com.gwtent.client.reflection;

public class NotFoundException extends RuntimeException{
	public NotFoundException(String msg){
		super(msg);
	}
	
	public NotFoundException(){
		super();
	}
}
