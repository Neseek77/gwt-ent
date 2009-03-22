package com.gwtent.client.test.validate;

import java.util.Set;

import javax.validation.InvalidConstraint;
import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.interfaces.IValidator;
import com.gwtent.client.test.GwtEntTestCase;
import com.gwtent.client.validate.impl.ValidatorGWT;

public class ValidateTestCase extends GwtEntTestCase{

  public void testValidate(){
    IValidator<Person> validator = GWT.create(Person.class);
    Person person = new Person();
    validator.validateProperty(person, "firstName", "a");
    validator.validate(person);
  }
  
  public void testValidateGWT(){
    Validator<Company> validator = new ValidatorGWT<Company>();
    Company company = new Company();
    Set<InvalidConstraint<Company>> ics = validator.validateProperty(company, "name");
    assertTrue(ics.size() > 0);
    
    ics = validator.validate(company);
    assertTrue(ics.size() > 0);
  }
  
  public void testValidateGWTGroup(){
    Validator<Company> validator = new ValidatorGWT<Company>();
    Company company = new Company();
    Set<InvalidConstraint<Company>> ics = validator.validateProperty(company, "name", "minimal");
    assertTrue(ics.size() > 0);
    
    ics = validator.validate(company);
    assertTrue(ics.size() > 0);
  }
  
}
