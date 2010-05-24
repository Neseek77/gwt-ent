package com.gwtent.client.test.uibinder;

import com.gwtent.client.test.common.GwtEntTestCase;
import com.gwtent.uibinder.client.GWTUIBinderRegister;

/**
 * 
 * @author James Luo
 *
 * 15/04/2010 10:05:09 AM
 */
public class DataBinderTestCase  extends GwtEntTestCase{
	
	@Override
  public String getModuleName() {
    return "com.gwtent.client.test.uibinder.UIBinder";
  }
	
	protected void gwtSetUp() throws Exception {
		GWTUIBinderRegister.register();
  }
	
	public void testUIBinder(){
    GWTUiBinder sreen = new GWTUiBinder();
    assert(sreen != null);
    
    //default value
    assert(sreen.testTextBox.getValue().equals("abc"));
    
    //change text box, 
    sreen.testTextBox.setValue("def", true);
    assert(sreen.theName.equals("def"));
    
    //change field
    sreen.theName = "xyz";
    sreen.dataBinder.modelChanged();
    assert(sreen.testTextBox.getValue().equals("xyz"));
    
    
//    sreen.getTestModel().setFirstName("set again");
//    sreen.getUIBinderManager().modelChanged();
//    assertOK(sreen);
//    
//    sreen.getTxtFirstName().setText("set by text box");
    //assertOK(sreen);
  }
}
