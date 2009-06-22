package com.gwtent.gen.template;

import javax.validation.groups.Default;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Button;
import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.uibinder.ModelRootAccessor;

public class TemplateSource extends HTMLTemplatePanel {

  protected Button btn = new Button();

  private static String getHTML() {
    // if compile HTML to source
    final String _HTML = "This is the HTML";
    // return _HTML

    // else read HTML from a URL
    String url = "";
    return "This is the HTML";
  }

  private void addElements() {
    // add elements through annotations
    // add(widget, id);

    //btn.setStylePrimaryName("style");
    btn.addStyleName("style");  //Some widgets not support setStylePrimaryName, ie EXTGWT
  }

  private void addEvents() {
    com.google.gwt.user.client.Element element = null;

    element = DOM.getElementById("");
    final int eventType = 1;
    if (element != null) {
      DOM.sinkEvents(element, Event.ONCLICK);

      DOM.setEventListener(element, new EventListener() {
        public void onBrowserEvent(Event event) {
          if (DOM.eventGetType(event) == eventType) {
            // invoke
          }
        }
      });
    }
  }
  
  private void _BindToEditor(){
    //for each all @UIBind
    getUIBinderManager().addBinder(btn, "path", false, TemplateSource.class, 
        new ModelRootAccessor(){
          public Object getValue() {
            return "";
          }

          public void setValue(Object value) {
            //
          }}, true, new Class<?>[]{Default.class});  //validate Groups
    
    //....
    
    doAfterBinderAllEditors();
  }
  
  private void _CodeFromHTML(){
  	//btn.setText("SetFromHTML");
    
  }
  
  private void _SetCSS(){
    //btn.addStyleName("ID");    
  }

  public TemplateSource() {
    super(getHTML());

    addElements();
    addEvents();
    _BindToEditor();
    _CodeFromHTML();
    _SetCSS();
  }

}
