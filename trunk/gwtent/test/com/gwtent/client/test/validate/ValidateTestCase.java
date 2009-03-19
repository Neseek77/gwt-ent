package com.gwtent.client.test.validate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;
import com.google.gwt.validation.client.interfaces.IValidator;
import com.gwtent.client.test.GwtEntTestCase;

public class ValidateTestCase extends GwtEntTestCase{

  public void testValidate(){
    IValidator<Person> validator = GWT.create(Person.class);
    Person person = new Person();
    validator.validate(person);
  }
  
}
