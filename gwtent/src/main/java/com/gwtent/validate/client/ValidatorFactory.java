package com.gwtent.validate.client;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidatorFactory {

	//private static ValidatorFactoryDefault factory = new ValidatorFactoryDefault();
	
	private ValidatorFactory(){
		
	}
	
	private static javax.validation.ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  public static Validator getGWTValidator() {
  
		//cache the factory somewhere
		Validator validator = factory.getValidator();
		
		return validator;
  }

}
