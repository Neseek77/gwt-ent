package com.gwtent.client.test.aop;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.aop.client.Aspectable;
import com.gwtent.client.test.aop.LogInterceptors.LogForAccountClick;
import com.gwtent.htmltemplate.client.HTMLEvent;
import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
import com.gwtent.htmltemplate.client.HTMLWidget;

@HTMLTemplate("/com/gwtent/client/test/template/testhtml.html")
public class AOPWithHTMLTemplate extends HTMLTemplatePanel implements Aspectable{

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
  
  @LogForAccountClick(value="")
  public void doLinkHanldByGWT(){
  	System.out.println("Link handle by GWT clicked.");
  }
  
  public AOPWithHTMLTemplate(String html) {
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
