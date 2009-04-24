package com.gwtent.client.test.uibinder;

import java.util.Set;

import javax.validation.InvalidConstraint;
import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.interfaces.IValidator;
import com.gwtent.client.test.GwtEntTestCase;
import com.gwtent.client.validate.impl.ValidatorGWT;

public class UIBinderTestCase extends GwtEntTestCase{

  private void assertOK(EditScreen sreen){
    assertTrue(sreen.getTxtFirstName().getText().equals(sreen.getTestModel().getFirstName()));
    assertTrue(sreen.getTxtBindToVariable().getText().equals(sreen.getVarToBind()));
  }
  
  private void assertOK(EditScreenUIBind sreen){
    assertTrue(sreen.getTxtFirstName().getText().equals(sreen.getTestModel().getFirstName()));
    assertTrue(sreen.getTxtBindToVariable().getText().equals(sreen.getVarToBind()));
  }
  
  public void testUIBinder_CreatedByCode(){
    EditScreen sreen = (EditScreen)GWT.create(EditScreen.class);
    sreen.modelChanged();
    assertOK(sreen);
    
    sreen.getTestModel().setFirstName("set again");
    sreen.getUIBinderManager().modelChanged();
    assertOK(sreen);
    
    //sreen.getTxtFirstName().setText("set by text box");
    //assertOK(sreen);
  }
  
  public void testUIBinder(){
    EditScreenUIBind sreen = (EditScreenUIBind)GWT.create(EditScreenUIBind.class);
    sreen.modelChanged();
    assertOK(sreen);
    
    sreen.getTestModel().setFirstName("set again");
    sreen.getUIBinderManager().modelChanged();
    assertOK(sreen);
    
    //sreen.getTxtFirstName().setText("set by text box");
    //assertOK(sreen);
  }
  
}
