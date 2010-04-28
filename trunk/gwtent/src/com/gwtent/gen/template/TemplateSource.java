package com.gwtent.gen.template;

import javax.validation.groups.Default;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Button;
import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.uibinder.DataBinder;
import com.gwtent.client.uibinder.ModelRootAccessor;

public class TemplateSource extends HTMLTemplatePanel {
	
	interface TemplateDataBinder extends com.gwtent.client.uibinder.DataBinder<TemplateSource>{
		
	}
	
	 
	
	public class TemplateSourceDataBinderImpl extends com.gwtent.client.uibinder.UIBinderManager<TemplateSource> implements TemplateDataBinder{

		public void bindAll(TemplateSource owner) {
		//for each all @UIBind
	    addBinder(owner.btn, "path", false, TemplateSource.class, 
	        new ModelRootAccessor(){
	          public Object getValue() {
	            return "";
	          }

	          public void setValue(Object value) {
	            //
	          }

						public String getRootPath() {
							return "rootPath";
						}}, true, new Class<?>[]{Default.class});  //validate Groups
	    
	    //....
	    
		}
		
	}
	
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

  protected void doSinkBrowserEvents() {
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
    }else
    	noSuchElementWhenSinkEvent("id");
  }
  
  private void _BindToEditor(){
    
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
    
    this.setUIBinderManager((com.gwtent.client.uibinder.DataBinder)GWT.create(TemplateDataBinder.class));
    this.getUIBinderManager().bindAll(this);
    doAfterBinderAllEditors();
    
    _CodeFromHTML();
    _SetCSS();
  }

}
