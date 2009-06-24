package com.gwtent.client.uibinder.binder;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.IValueChangedOutSideListener;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;

public class TextBoxBinder extends AbstractUIBinder<TextBoxBase, String> {
  
  public static class BinderMetaData implements IBinderMetaData<TextBoxBase, String>{

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
  
  public class ValueChangedListener implements IValueChangedOutSideListener{

    public void valueChanged() {
      doValueChanged();
    }
    
  }
  
  public class ChangeListenerImpl implements ChangeListener{
    public void onChange(Widget sender) {
    	setEditorToValue(getWidget().getText());
    }
  }
  

	@Override
	protected void doInit(TextBoxBase widget, ModelValue<String> value) {
		widget.addChangeListener(new ChangeListenerImpl());
	}


	@Override
	protected void setValueToEditor(String value, TextBoxBase widget) {
		widget.setText(value);
	}


  
}
