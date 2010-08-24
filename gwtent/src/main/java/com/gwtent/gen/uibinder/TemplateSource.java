package com.gwtent.gen.uibinder;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.TextBox;
import com.gwtent.htmltemplate.client.HTMLWidget;
import com.gwtent.reflection.client.Reflection;
import com.gwtent.ui.client.transition.example.Sex;
import com.gwtent.uibinder.client.GWTUIBinderRegister;
import com.gwtent.uibinder.client.ModelRootAccessor;
import com.gwtent.uibinder.client.UIBinderManager;

public class TemplateSource {

	class MyUiBinderManager extends UIBinderManager{

		public void bindAll(Object owner) {
			// TODO Auto-generated method stub
			
		}

	}
  private static MyUiBinderManager uiBinder = GWT.create(MyUiBinderManager.class);


  public class MYUiBinderManager_Gen extends MyUiBinderManager{
  	
  }
  
	public static class TestModel implements Reflection {
	  private String firstName;
	  
	  private String lastName;
	  
	  private Date dateOfBirth;
	  
	  private Sex sex;

	  public void setFirstName(String firstName) {
	    this.firstName = firstName;
	  }

	  public String getFirstName() {
	    return firstName;
	  }

	  public void setLastName(String lastName) {
	    this.lastName = lastName;
	  }

	  public String getLastName() {
	    return lastName;
	  }

	  public void setDateOfBirth(Date dateOfBirth) {
	    this.dateOfBirth = dateOfBirth;
	  }

	  public Date getDateOfBirth() {
	    return dateOfBirth;
	  }

	  public void setSex(Sex sex) {
	    this.sex = sex;
	  }

	  public Sex getSex() {
	    return sex;
	  }
	}
	
  private TestModel testModel = null;

  public TemplateSource() {
   
    GWTUIBinderRegister.register();
    
    testModel = new TestModel();
    testModel.setFirstName("first name set by code");
    
    //This function do nothing, cause binding system has not been initialized
    uiBinder.modelChanged();
    
    uiBinder.addBinder(txtBindToVariable, "", false, String.class,
        new ModelRootAccessor(){

          public Object getValue() {
            return varToBind;
          }

          public void setValue(Object value) {
            varToBind = (String)value;
          }

					public String getRootPath() {
						return "varToBind";
					}}, false);
    
    uiBinder.addBinder(txtFirstName, "firstName", false, TestModel.class,
        new ModelRootAccessor(){

          public Object getValue() {
            return testModel;
          }

          public void setValue(Object value) {
            //
          }

					public String getRootPath() {
						return "firstName";
					}}, false);
    //UIBinderFactory.getUIBinder(TextBox.class).binder(txtFirstName, this, testModel, "testModel.firstName");
  }
  
  @HTMLWidget
  //@UIBind(path="testModel.firstName")
  protected TextBox txtFirstName = new TextBox();
  
  @HTMLWidget
  //@UIBind(path="varToBind")
  protected TextBox txtBindToVariable = new TextBox();
  
  public TextBox getTxtBindToVariable() {
    return txtBindToVariable;
  }

  public void setTxtBindToVariable(TextBox txtBindToVariable) {
    this.txtBindToVariable = txtBindToVariable;
  }

  private String varToBind = "abc";

  public TextBox getTxtFirstName() {
    return txtFirstName;
  }

  public void setTxtFirstName(TextBox txtFirstName) {
    this.txtFirstName = txtFirstName;
  }

  public void setTestModel(TestModel testModel) {
    this.testModel = testModel;
  }

  public TestModel getTestModel() {
    return testModel;
  }

  public void setVarToBind(String varToBind) {
    this.varToBind = varToBind;
  }

  public String getVarToBind() {
    return varToBind;
  }

}
