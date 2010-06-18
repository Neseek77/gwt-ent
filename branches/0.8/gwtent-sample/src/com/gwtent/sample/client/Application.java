package com.gwtent.sample.client;

import java.lang.annotation.Annotation;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.client.validate.ui.ErrorMessageBox;
import com.gwtent.client.validate.ui.ErrorMessagePanel;
import com.gwtent.sample.client.Phone.Receiver;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

  public void testReflection(){
    TestReflection test = new TestReflection();
    ClassType classType = TypeOracle.Instance.getClassType(TestReflection.class);
    
    test.setString("set by code");
    System.out.println("after SetByCode:" + test.getString());
    System.out.println("after SetByCode:" + classType.invoke(test, "getString", null));
    
    classType.invoke(test, "setString", new Object[]{"set by invoke"});
    System.out.println("after SetByInvoke:" + classType.invoke(test, "getString", null));
  }
  
  public void addAnnotations(Annotation[] annotations){
    
  }
  
  public void testAOP(){
  	if (this instanceof EntryPoint)
  		System.out.println("True");
  	else
  		System.out.println("False");
  	
  	Phone phone = (Phone) GWT.create(Phone.class);
		Receiver auntJane = phone.call(123456789);
		System.out.println(auntJane);
  }
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
//    testReflection();
//    testAOP();

    final Button button = new Button("Click me");
    final TextArea label = new TextArea();

    button.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        label.setText("");
      }
    });

    // Assume that the host HTML has elements defined whose
    // IDs are "slot1", "slot2".  In a real app, you probably would not want
    // to hard-code IDs.  Instead, you could, for example, search for all 
    // elements with a particular CSS class and replace them with widgets.
    //
    //RootPanel.get("slot1").add(button);
    //RootPanel.get("slot2").add(label);
    
    errorPnl = new ErrorMessagePanel();
    errorPnl.addErrorMsg("Hello, you must input abcedfadfasffsdfasfasf.");
    errorPnl.addErrorMsg("Other one message");
    
    validateBtn.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				errorPnl.showPanel(text.getElement());
			}});
    
    
    
    RootPanel.get("validator").add(text);
    RootPanel.get("validatorBtn").add(validateBtn);
    //RootPanel.get("panelTest").add(errorPnl);
  }
  
  private ErrorMessagePanel errorPnl = null;
  private Button validateBtn = new Button("Validate");
  private TextBox text = new TextBox();
  
  static class Test extends Object {
    public void dummy() {
    }
    
    /**
     * @Caption Name
     */
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

}
