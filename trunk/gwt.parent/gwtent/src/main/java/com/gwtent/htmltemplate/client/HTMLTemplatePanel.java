package com.gwtent.htmltemplate.client;

import java.util.NoSuchElementException;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.uibinder.client.DataBinder;
import com.gwtent.uibinder.client.IValueChangedByBindingListener;

/**
 * HTMLTemplatePanel
 * 
 * All subclasses of HTMLTemplatePanel will generate light reflection information
 * No Super classes, No relation class types
 * You can override it by place new @Reflectable annotation.
 * 
 * CSS Style: 
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */

//@dReflectable(relationTypes=false, superClasses=false, assignableClasses=true)
public class HTMLTemplatePanel extends HTMLPanel {

  private static int idCount = 0;
  private boolean renameIdWhenAddWidget = true;
  
  public HTMLTemplatePanel(String html) {
    super(html);
    
    this.addStyleName("gwtent-HTMLTemplatePanel");
    

  }
  
  
  private DataBinder uiBinderManager = null;
  
  public DataBinder getUIBinderManager() {
  	if (uiBinderManager == null){
  		throw new RuntimeException("DataBinder not init yet, please do what you want by override \"protected void doAfterBinderAllEditors()\" function.");
  	}
    return uiBinderManager;
  }
  
  protected void setUIBinderManager(DataBinder uiBinderManager){
  	this.uiBinderManager = uiBinderManager;
  }
  
  protected void onLoad() {
    //doSinkBrowserEvents();
  }
  
  protected void doSinkBrowserEvents(){
  	
  }
  
  /**
   * Call here if you changed model by your code
   * PLEASE NOTE: DO NOT call this function in your constructor
   * because the binding system has not been initialized
   * 
   */
  public void modelChanged(String... pathPrefixs){
  	getUIBinderManager().modelChanged(pathPrefixs);
  }
  
  
  /**
   * 
   * @param pathPrefixs, the Prefix of path, if null, all path match
   * @param showMessagesToUI
   * @param validateGroups
   * @return
   */
  public Set<ConstraintViolation<Object>> validate(String[] pathPrefixs, boolean showMessagesToUI, Class<?>... validateGroups){
  	return getUIBinderManager().validate(pathPrefixs, showMessagesToUI, validateGroups);
  }
  
  /**
   * Validate this page, you can override with your version
   * 
   * @param showMessagesToUI
   * @param validateGroups
   * @return
   */
  public Set<ConstraintViolation<Object>> validate(boolean showMessagesToUI, Class<?>... validateGroups){
  	return getUIBinderManager().validate(showMessagesToUI, validateGroups);
  }
  
  public Set<ConstraintViolation<Object>> validate(UIObject widget, boolean showMessagesToUI, Class<?>... validateGroups){
  	return getUIBinderManager().validate(widget, showMessagesToUI, validateGroups);
  }
  
  public void hideAllValidateMessages(){
  	getUIBinderManager().hideAllValidateMessages();
  }

  public void removeValueChangedByBindingListener(IValueChangedByBindingListener listener){
  	getUIBinderManager().removeValueChangedByBindingListener(listener);
  }
  
  public void addValueChangedByBindingListener(IValueChangedByBindingListener listener){
  	getUIBinderManager().addValueChangedByBindingListener(listener);
  }
  
  
  /**
   * Can override this function.
   * This function will be called by generated code 
   * when all editor finished binding.
   */
  protected void doAfterBinderAllEditors(){
  }

  
  public void add(Widget widget, String id) {
    if (widget != null){
//      widget.setWidth("100%");
      try {
        super.add(widget, id);
      } catch (NoSuchElementException e) {
        throw new NoSuchElementException(e.getMessage() + " If you modify HTML out of Eclipse, please refresh the HTML folder in eclipse before you run this application.");
      }
      
      //this.getElement().getChildNodes()
      //TODO Should rename all nodes, not just the nodes added here
      if (renameIdWhenAddWidget){
      	getElementById(id).setId(id + idCount);  //if HTMLPanel instance twice? the id will be same which will cause problems when add widget by id
	      idCount++;
      }
    }else{
    	noSuchElement("Widget is null: " + id);
    }
  }
  
  protected void noSuchElement(String msg){
  	throw new NoSuchElementException(msg);
  }
  
  protected void noSuchElementWhenSinkEvent(String id){
  	noSuchElement("No such elemnet when Handle event to element, wrong id or this panel may no attach to document, id: " + id);
  }

	public void setRenameIdWhenAddWidget(boolean renameIdWhenAddWidget) {
		this.renameIdWhenAddWidget = renameIdWhenAddWidget;
	}

	public boolean isRenameIdWhenAddWidget() {
		return renameIdWhenAddWidget;
	}

}
