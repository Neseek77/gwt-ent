package com.gwtent.client.template;

import java.util.NoSuchElementException;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class HTMLTemplatePanel extends HTMLPanel {

  public HTMLTemplatePanel(String html) {
    super(html);
  }
  
//  public HTMLTemplatePanel() {
//    super("");
//  }
  
  public void add(Widget widget, String id) {
    if (widget != null){
      widget.setWidth("100%");
      super.add(widget, id);
    }else{
      throw new NoSuchElementException("add widget to html, id: " + id);
    }
  }

}
