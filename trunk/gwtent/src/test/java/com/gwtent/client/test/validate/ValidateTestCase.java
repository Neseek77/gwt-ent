package com.gwtent.client.test.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.gwtent.client.test.common.GwtEntTestCase;
import com.gwtent.client.test.validate.Groups.Billable;
import com.gwtent.client.test.validate.Groups.BuyInOneClick;
import com.gwtent.validate.client.GWTValidateMessageStore;
import com.gwtent.validate.client.ValidatorFactory;
import com.gwtent.validate.client.impl.ValidatorGWT;
import com.gwtent.validate.client.message.ValidateMessages;

public class ValidateTestCase extends GwtEntTestCase{
	
	public String getModuleName() {
    return "com.gwtent.client.test.validate.Validate";
  }

  public void testValidate(){

  }
  
  protected void gwtSetUp() throws Exception {
  	
  }
  
  private boolean isMessageExists(Set<ConstraintViolation<User>> ics, String message){
  	for (ConstraintViolation<User> c : ics){
  		if (c.getMessage().equals(message))
  			return true;
  	}
  	
  	return false;
  }
  
  public void testValidateGWT(){
  	ValidateMessages message = GWT.create(ValidateMessages.class);
  	GWTValidateMessageStore.getInstance().addMessageObject(message, ValidateMessages.class);
  	
    Validator validator = ValidatorFactory.getGWTValidator();
    User user = new User();
    Set<ConstraintViolation<User>> ics = validator.validateProperty(user, "name");
    assertTrue(ics.size() == 3);
    assertTrue(isMessageExists(ics, "must not be empty"));
    assertTrue(isMessageExists(ics, "must not be null"));
    assertTrue(isMessageExists(ics, "size must be between 8 and 200"));
    
    ics = validator.validate(user, BuyInOneClick.class);
    assertTrue(ics.size() == 6);
  }
  
  public void testValidateGWTGroup(){
  	GWTValidateMessageStore.getInstance().addMessageObject(GWT.create(ValidateMessages.class), ValidateMessages.class);
  	
    Validator validator = ValidatorFactory.getGWTValidator();
    User user = new User();
    
    Set<ConstraintViolation<User>> ics = validator.validateProperty(user, "name");
    assertTrue(ics.size() == 3);
    
    ics = validator.validate(user, Billable.class);
    assertTrue(ics.size() == 4);
    
    ics = validator.validate(user, BuyInOneClick.class);
    assertTrue(ics.size() == 6);
    
  }
  
  public void testValidatePropertyPath(){
  	Validator validator = ValidatorFactory.getGWTValidator();
    User user = new User();
    
    try {
			Set<ConstraintViolation<User>> ics = validator.validateProperty(user, "address.postCode");
			//should get error, path return null
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		
		user.setAddress(new Address());
		Set<ConstraintViolation<User>> ics = validator.validateProperty(user, "address.postCode");
		assertTrue(ics.size() == 1);
  }
  
}
