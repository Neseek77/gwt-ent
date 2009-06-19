package com.gwtent.client.test.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.gwtent.client.test.GwtEntTestCase;
import com.gwtent.client.validate.GWTValidateMessageStore;
import com.gwtent.client.validate.impl.ValidatorGWT;
import com.gwtent.client.validate.message.ValidateMessages;

public class ValidateTestCase extends GwtEntTestCase{

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
  	
    Validator validator = new ValidatorGWT();
    User company = new User();
    Set<ConstraintViolation<User>> ics = validator.validateProperty(company, "name");
    assertTrue(ics.size() > 0);
    assertTrue(isMessageExists(ics, "must not be empty"));
    assertTrue(isMessageExists(ics, "must not be null"));
    
    ics = validator.validate(company);
    assertTrue(ics.size() > 0);
  }
  
  public void testValidateGWTGroup(){
  	GWTValidateMessageStore.getInstance().addMessageObject(GWT.create(ValidateMessages.class), ValidateMessages.class);
  	
    Validator validator = new ValidatorGWT();
    User company = new User();
    
    Set<ConstraintViolation<User>> ics = validator.validateProperty(company, "name");
    assertTrue(ics.size() > 0);
    
    ics = validator.validate(company);
    assertTrue(ics.size() > 0);
  }
  
}
