package com.gwtent.client.uibinder;

import com.google.gwt.user.client.ui.Widget;

public interface UIBinder {
  /**
   * 
   * @param widget the widget, ie: TextBox
   * @param owner the owner, usually contains widgets and models
   * @param path the path, ie: persion.company.name
   */
  public void init(Widget widget, Object owner, String path);
  public void binder();
}
