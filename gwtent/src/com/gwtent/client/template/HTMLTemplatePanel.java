package com.gwtent.client.template;

import java.util.NoSuchElementException;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class HTMLTemplatePanel extends HTMLPanel {

  private static int idCount = 0;
  
  public HTMLTemplatePanel(String html) {
    super(html);
  }
  
//  public HTMLTemplatePanel() {
//    super("");
//  }
  
  public void add(Widget widget, String id) {
    if (widget != null){
//      widget.setWidth("100%");
      super.add(widget, id);
      //this.getElement().getChildNodes()
      //TODO Should rename all nodes, not just the nodes added here
      getElementById(id).setId(id + idCount);  //if HTMLPanel instance twice? the id will be same which will cause problems when add widget by id
      idCount++;
    }else{
      throw new NoSuchElementException("add widget to html, id: " + id);
    }
  }

}
