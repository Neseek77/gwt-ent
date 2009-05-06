package com.gwtent.client.test.uibinder;

import com.google.gwt.user.client.ui.TextBox;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.client.template.UIBind;
import com.gwtent.client.uibinder.GWTUIBinderRegister;
import com.gwtent.client.uibinder.ModelRootAccessor;
import com.gwtent.client.uibinder.UIBinderGWTFactory;
import com.gwtent.client.uibinder.UIBinderManager;
import com.gwtent.client.uibinder.UIBinderManager.ModelCallback;

@HTMLTemplate("testhtml.html")
public class EditScreen extends HTMLTemplatePanel {

  private TestModel testModel = null;

  public EditScreen(String html) {
    super(html);
    
    GWTUIBinderRegister.register();
    
//    uiBinderManager.addBinder(txtFirstName, "firstName", false, TestModel.class);
    
    testModel = new TestModel();
    testModel.setFirstName("first name set by code");
    
    //This function do nothing, cause binding system has not been initialized
    modelChanged();
    
    this.getUIBinderManager().addBinder(txtBindToVariable, "", false, String.class,
        new ModelRootAccessor(){

          public Object getValue() {
            return varToBind;
          }

          public void setValue(Object value) {
            varToBind = (String)value;
          }});
    
    getUIBinderManager().addBinder(txtFirstName, "firstName", false, TestModel.class,
        new ModelRootAccessor(){

          public Object getValue() {
            return testModel;
          }

          public void setValue(Object value) {
            //
          }});
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
