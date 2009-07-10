package com.gwtent.client.uibinder.binder;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;
import com.gwtent.client.uibinder.AbstractUIBinder.EditorToValueSetException;

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
		widget.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				setEditorToValue(getWidget().isChecked());
			}});
	}


	@Override
	protected void setValueToEditor(Boolean value, CheckBox widget) {
		if (value != null)
			widget.setChecked(value);
		else
			widget.setChecked(false);
	}
}
