package com.gwtent.client.uibinder.binder;

import com.google.gwt.user.client.ui.Label;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;

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
  
  protected void doValueChangedByWidget(){
  	//Label never change
    //this.getModelValue().setValue(this.getWidget().getHTML());
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
