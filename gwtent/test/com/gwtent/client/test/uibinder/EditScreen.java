package com.gwtent.client.test.uibinder;

import com.google.gwt.user.client.ui.TextBox;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.client.uibinder.UIBind;

@HTMLTemplate("testhtml.html")
public class EditScreen extends HTMLTemplatePanel {

  private TestModel testModel = null;
  
  public EditScreen(String html) {
    super(html);
  }
  
  @HTMLWidget
  @UIBind(path="testModel.firstName")
  protected TextBox txtFirstName = new TextBox();

  public void setTestModel(TestModel testModel) {
    this.testModel = testModel;
  }

  public TestModel getTestModel() {
    return testModel;
  }

}
