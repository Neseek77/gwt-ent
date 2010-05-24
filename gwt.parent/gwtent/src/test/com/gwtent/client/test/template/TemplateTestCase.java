package com.gwtent.client.test.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.test.common.GwtEntTestCase;
import com.gwtent.htmltemplate.client.HTMLEvent;
import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
import com.gwtent.htmltemplate.client.HTMLWidget;

public class TemplateTestCase extends GwtEntTestCase {
	
	public String getModuleName() {
    return "com.gwtent.client.test.template.Template";
  }
	

  public interface HTMLTemplateFile{
    public String getTemplateFileName();
  }  
  
  @HTMLTemplate("testhtml.html")
  public static class TestHTMLTemplate extends HTMLTemplatePanel{

    @HTMLWidget
    protected Button btn = new Button();
    
    @HTMLWidget
    protected Hyperlink link = new Hyperlink();
    
    @HTMLWidget("btn1")
    protected Button getButton(){
      return new Button();
    }
    
    @HTMLEvent("linkHanldByGWT")
    protected void onLinkClickEvent(){
      
    }
    
    public TestHTMLTemplate(String html) {
      super(html);
      
      Element element = btn.getElement();
      DOM.sinkEvents(element, Event.ONCLICK);
      DOM.setEventListener(element,new EventListener(){
        public void onBrowserEvent(Event event) {
            if(DOM.eventGetType(event) == Event.ONCLICK){
                Window.alert("hello world!");
            }
            }
        });
      
      
      btn.setText("Hello Template World");
      
      link.addClickListener(new ClickListener(){

        public void onClick(Widget sender) {
          Window.alert("Hello");
        }
        
      });
    }
    
  }
  

  
  public void testTemplate(){
    TestHTMLTemplate template = (TestHTMLTemplate) GWT.create(TestHTMLTemplate.class);
    assertTrue(template.btn.getText().equals("SetFromHTML"));
    System.out.print(template);
  }
  
  
  @HTMLTemplate("com/gwtent/client/test/template/docs/testhtml_abs.html")
  public static class TestHTMLAbsPathTemplate extends TestHTMLTemplate{

    public TestHTMLAbsPathTemplate(String html) {
      super(html);
    }
    
  }
  
  public void testAbsTemplate(){
    TestHTMLAbsPathTemplate template = (TestHTMLAbsPathTemplate) GWT.create(TestHTMLAbsPathTemplate.class);
    System.out.print(template);
  }
  
  
  @HTMLTemplate(value="testhtml_vars.html", variables = {"name=GWTENT"})
  public static class TestHTMLTemplateVariables extends TestHTMLTemplate{

    
    public TestHTMLTemplateVariables(String html) {
      super(html);
    }
    
  }
  
  public void testTemplateVariables(){
    TestHTMLTemplateVariables template = (TestHTMLTemplateVariables) GWT.create(TestHTMLTemplateVariables.class);
    assertTrue(template.toString().contains("you name is GWTENT"));
  }
  
  @HTMLTemplate("testhtml.html")
  public static class TestHTMLTemplateCreateElementInConstructor extends HTMLTemplatePanel{

    @HTMLWidget
    protected Button btn = null;
    
    public TestHTMLTemplateCreateElementInConstructor(String html) {
      super(html);
    
      btn = new Button();
      btn.setText("Hello Template World");
    }
    
  }
  
  public void testTemplateCreateElementInConstructor(){
    TestHTMLTemplateCreateElementInConstructor template = (TestHTMLTemplateCreateElementInConstructor) GWT.create(TestHTMLTemplateCreateElementInConstructor.class);
    System.out.print(template);
  }
  
  public static class TestHTMLTemplateInherited extends TestHTMLTemplateCreateElementInConstructor{

    
    @HTMLWidget("btn1")
    protected Button getButton(){
      return new Button();
    }
    
    public TestHTMLTemplateInherited(String html) {
      super(html);
    }
  }
  
  public void testTemplateInherited(){
    TestHTMLTemplateInherited template = (TestHTMLTemplateInherited) GWT.create(TestHTMLTemplateInherited.class);
    System.out.print(template);
  }
  
  @HTMLTemplateTest("testhtml.html")
  public static class TestHTMLTemplateAnnotationInherited extends HTMLTemplatePanel{

    @HTMLWidget
    protected Button btn = new Button();
    
    @HTMLWidget
    protected Hyperlink link = new Hyperlink();
    
    @HTMLWidget("btn1")
    protected Button getButton(){
      return new Button();
    }
    
    @HTMLEvent("linkHanldByGWT")
    protected void onLinkClickEvent(){
      
    }
    
    public TestHTMLTemplateAnnotationInherited(String html) {
      super(html);
      
      Element element = btn.getElement();
      DOM.sinkEvents(element, Event.ONCLICK);
      DOM.setEventListener(element,new EventListener(){
        public void onBrowserEvent(Event event) {
            if(DOM.eventGetType(event) == Event.ONCLICK){
                Window.alert("hello world!");
            }
            }
        });
      
      btn.setText("Hello Template World");
      
      link.addClickListener(new ClickListener(){

        public void onClick(Widget sender) {
          Window.alert("Hello");
        }
      });
    }
    
  }
  
  public void testTemplateAnnotationInherited(){
  	TestHTMLTemplateAnnotationInherited template = (TestHTMLTemplateAnnotationInherited) GWT.create(TestHTMLTemplateAnnotationInherited.class);
    System.out.print(template);
  }

}
