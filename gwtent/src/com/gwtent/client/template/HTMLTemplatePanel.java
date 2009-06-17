package com.gwtent.client.template;

import java.util.NoSuchElementException;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.uibinder.UIBinderManager;

/**
 * HTMLTemplatePanel
 * 
 * CSS Style: 
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
public class HTMLTemplatePanel extends HTMLPanel {

  private static int idCount = 0;
  
  public HTMLTemplatePanel(String html) {
    super(html);
    
    this.addStyleName("gwtent-HTMLTemplatePanel");
  }
  
  
  private UIBinderManager uiBinderManager = new UIBinderManager();
  
  public UIBinderManager getUIBinderManager() {
    return uiBinderManager;
  }
  
  /**
   * Call here if you changed model by your code
   * PLEASE NOTE: DO NOT call this function in your constructor
   * because the binding system has not been initialized
   * 
   */
  public void modelChanged(String... pathPrefixs){
    uiBinderManager.modelChanged(pathPrefixs);
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
      getElementById(id).setId(id + idCount);  //if HTMLPanel instance twice? the id will be same which will cause problems when add widget by id
      idCount++;
    }else{
      throw new NoSuchElementException("add widget to html, id: " + id);
    }
  }

}
