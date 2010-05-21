package com.gwtent.client.test.uibinder;

import com.google.gwt.user.client.ui.TextBox;
import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
import com.gwtent.htmltemplate.client.HTMLWidget;
import com.gwtent.uibinder.client.GWTUIBinderRegister;
import com.gwtent.uibinder.client.ModelRootAccessor;
import com.gwtent.uibinder.client.UIBind;
import com.gwtent.uibinder.client.UIBinderGWTFactory;
import com.gwtent.uibinder.client.UIBinderManager;
import com.gwtent.uibinder.client.UIBinderManager.ModelCallback;

@HTMLTemplate("testhtml.html")
public class EditScreen extends HTMLTemplatePanel {

  private TestModel testModel = null;

  public EditScreen(String html) {
    super(html);
    
    GWTUIBinderRegister.register();
    
//    uiBinderManager.addBinder(txtFirstName, "firstName", false, TestModel.class);
    
    testModel = new TestModel();
    testModel.setFirstName("first name set by code");
    
    
    setUIBinderManager(new UIBinderManager(){

			public void bindAll(Object owner) {
				addBinder(txtBindToVariable, "", false, String.class,
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
		    
		    addBinder(txtFirstName, "firstName", false, TestModel.class,
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
			}});
    
    
    //UIBinderFactory.getUIBinder(TextBox.class).binder(txtFirstName, this, testModel, "testModel.firstName");
  }
  
  protected void doAfterBinderAllEditors(){
  	this.getUIBinderManager().addBinder(txtBindToVariable, "", false, String.class,
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
		    
  	this.getUIBinderManager().addBinder(txtFirstName, "firstName", false, TestModel.class,
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
