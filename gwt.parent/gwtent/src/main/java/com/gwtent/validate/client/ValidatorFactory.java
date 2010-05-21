package com.gwtent.validate.client;

import javax.validation.Validator;

import com.gwtent.validate.client.impl.ValidatorGWT;

public class ValidatorFactory {

	//private static ValidatorFactoryDefault factory = new ValidatorFactoryDefault();
	
	private ValidatorFactory(){
		
	}
	
	private static ValidatorGWT validatorGWT = new ValidatorGWT();
  public static Validator getGWTValidator() {
  	return validatorGWT;
  }

}
