package com.gwtent.uibinder.client.binder;

import com.google.gwt.user.client.ui.Label;
import com.gwtent.common.client.ObjectFactory;
import com.gwtent.uibinder.client.AbstractUIBinder;
import com.gwtent.uibinder.client.IBinderMetaData;
import com.gwtent.uibinder.client.ModelValue;
import com.gwtent.uibinder.client.UIBinder;

public class LabelBinder extends AbstractUIBinder<Label, Object> {

  public static class BinderMetaData implements IBinderMetaData<Label, Object>{

    public ObjectFactory<UIBinder<Label, Object>> getFactory() {
      return new ObjectFactory<UIBinder<Label, Object>>(){

        public UIBinder<Label, Object> getObject() {
          return new LabelBinder();
        }};
    }

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{Label.class};
    }
  }
  
  protected void doInit(final Label widget, final ModelValue<Object> value) {
    
  }

  protected void setValueToEditor(Object value, Label widget) {
    if (value != null)
      widget.setText(value.toString());
    else
      widget.setText("");
    
  }

}
