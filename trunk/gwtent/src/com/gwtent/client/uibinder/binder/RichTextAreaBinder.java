package com.gwtent.client.uibinder.binder;

import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;

public class RichTextAreaBinder extends AbstractUIBinder<RichTextArea, String> {

  public static class BinderMetaData implements IBinderMetaData<RichTextArea, String>{

    public ObjectFactory<UIBinder<RichTextArea, String>> getFactory() {
      return new ObjectFactory<UIBinder<RichTextArea, String>>(){

        public UIBinder<RichTextArea, String> getObject() {
          return new RichTextAreaBinder();
        }};
    }

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{RichTextArea.class};
    }
    
  }
  
  protected void doValueChangedByWidget(){
    this.getModelValue().setValue(this.getWidget().getHTML());
  }
  
  protected void doInit(final RichTextArea widget, final ModelValue<String> value) {
    widget.addFocusListener(new FocusListener(){

      public void onFocus(Widget sender) {
      }

      public void onLostFocus(Widget sender) {
        doValueChangedByWidget();
      }});
    
    widget.addKeyboardListener(new KeyboardListener(){

      public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        if (keyCode == KeyboardListener.KEY_ENTER)
          doValueChangedByWidget();
      }

      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
      }});
  }

  protected void setValueToEditor(String value, RichTextArea widget) {
    if (value != null)
      widget.setHTML(value);
    else
      widget.setHTML("");
    
  }

}
