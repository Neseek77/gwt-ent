package com.gwtent.client.uibinder.binder;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.IValueChangedListener;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;

public class TextBoxBinder implements UIBinder<TextBoxBase, String> {
  
  public static class SupportedEditors implements IBinderMetaData<TextBoxBase, String>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{TextBox.class, TextArea.class, PasswordTextBox.class};
    }

    public ObjectFactory<UIBinder<TextBoxBase, String>> getFactory() {
      return new ObjectFactory<UIBinder<TextBoxBase, String>>(){

        public UIBinder<TextBoxBase, String> getObject() {
          return new TextBoxBinder();
        }};
    }

    public Class<?>[] getSupportedValueTypes() {
      return new Class<?>[]{String.class};
    }
  }
  
  public class ValueChangedListener implements IValueChangedListener{

    public void valueChanged() {
      doValueChanged();
    }
    
  }

  private TextBoxBase widget;
  private ModelValue value;
  
  public class ChangeListenerImpl implements ChangeListener{
    public void onChange(Widget sender) {
      //if validate true
      value.setValue(widget.getText());
    }
  }
  
  public void binder(TextBoxBase widget, ModelValue value) {
    this.widget = widget;
    this.value = value;
    
    value.addValueChangedListener(new ValueChangedListener());
    
    widget.addChangeListener(new ChangeListenerImpl());
    
    doValueChanged();
  }
 
  
  protected void doValueChanged(){
    Object tmpValue = value.getValue();
    if (tmpValue != null)
      widget.setText(tmpValue.toString());
    else
      widget.setText("");
  }

  
}
