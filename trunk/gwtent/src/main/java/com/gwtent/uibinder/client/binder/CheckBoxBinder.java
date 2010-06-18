package com.gwtent.uibinder.client.binder;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.gwtent.common.client.ObjectFactory;
import com.gwtent.uibinder.client.AbstractUIBinder;
import com.gwtent.uibinder.client.IBinderMetaData;
import com.gwtent.uibinder.client.ModelValue;
import com.gwtent.uibinder.client.UIBinder;

public class CheckBoxBinder extends AbstractUIBinder<CheckBox, Boolean> {
  
  public static class SupportedEditors implements IBinderMetaData<CheckBox, Boolean>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{CheckBox.class};
    }

    public ObjectFactory<UIBinder<CheckBox, Boolean>> getFactory() {
      return new ObjectFactory<UIBinder<CheckBox, Boolean>>(){

        public UIBinder<CheckBox, Boolean> getObject() {
          return new CheckBoxBinder();
        }};
    }
  }
  

	@Override
	protected void doInit(CheckBox widget, ModelValue<Boolean> value) {
		widget.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				setEditorToValue(event.getValue());
			}});
	}


	@Override
	protected void setValueToEditor(Boolean value, CheckBox widget) {
		if (value != null)
			widget.setValue(value);
		else
			widget.setValue(false);
	}
}
