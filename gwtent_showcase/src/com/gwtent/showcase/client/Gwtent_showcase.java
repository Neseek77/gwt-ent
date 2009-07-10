package com.gwtent.showcase.client;

import java.lang.annotation.Annotation;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.showcase.client.aop.Phone;
import com.gwtent.showcase.client.aop.Phone.Receiver;
import com.gwtent.showcase.client.reflection.TestReflection;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwtent_showcase implements EntryPoint {
	
	/**
   * The type passed into the
   * {@link com.google.gwt.sample.showcase.generator.ShowcaseGenerator}.
   */
  private static final class GeneratorInfo {
  }

  public void testReflection(){
    com.gwtent.showcase.client.reflection.TestReflection test = new TestReflection();
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
  	Phone phone = (Phone) GWT.create(Phone.class);
		Receiver auntJane = phone.call(123456789);
		System.out.println(auntJane);
  }
  
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.get().add((MainPageHTMLPanel)GWT.create(MainPageHTMLPanel.class));
	}

}
