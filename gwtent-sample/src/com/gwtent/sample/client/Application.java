package com.gwtent.sample.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.gwtent.client.reflection.ClassType;

import java.lang.annotation.Annotation;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

  public void testReflection(){
    TestReflection test = new TestReflection();
    ClassType classType = (ClassType)GWT.create(TestReflection.class);
    
    test.setString("set by code");
    System.out.println("after SetByCode:" + test.getString());
    System.out.println("after SetByCode:" + classType.invoke(test, "getString", null));
    
    classType.invoke(test, "setString", new Object[]{"set by invoke"});
    System.out.println("after SetByInvoke:" + classType.invoke(test, "getString", null));
  }
  
  public void addAnnotations(Annotation[] annotations){
    
  }
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    testReflection();

    final Button button = new Button("Click me");
    final Label label = new Label();

    button.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        if (label.getText().equals(""))
          label.setText("Hello World!");
        else
          label.setText("");
      }
    });

    // Assume that the host HTML has elements defined whose
    // IDs are "slot1", "slot2".  In a real app, you probably would not want
    // to hard-code IDs.  Instead, you could, for example, search for all 
    // elements with a particular CSS class and replace them with widgets.
    //
    RootPanel.get("slot1").add(button);
    RootPanel.get("slot2").add(label);
  }
  
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
