package com.gwtent.client.uibinder.binder;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;
import com.gwtent.client.uibinder.AbstractUIBinder.EditorToValueSetException;

public class DateBoxBinder extends AbstractUIBinder<DateBox, Date> {
  
  public static class BinderMetaData implements IBinderMetaData<DateBox, Date>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{DateBox.class};
    }

    public ObjectFactory<UIBinder<DateBox, Date>> getFactory() {
      return new ObjectFactory<UIBinder<DateBox, Date>>(){

        public UIBinder<DateBox, Date> getObject() {
          return new DateBoxBinder();
        }};
    }
  }
  

	@Override
	protected void doInit(DateBox widget, ModelValue<Date> value) {
		widget.addValueChangeHandler(new ValueChangeHandler<Date>(){
			public void onValueChange(ValueChangeEvent<Date> event) {
				setEditorToValue(getWidget().getValue());
			}});
	}


	@Override
	protected void setValueToEditor(Date value, DateBox widget) {
		widget.setValue(value);
	}
}
