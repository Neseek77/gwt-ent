package com.gwtent.client.uibinder.binder;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;

/**
 * The binder for all class whiches has implement HasValue
 * 
 * @author James Luo
 *
 * 15/04/2010 3:20:07 PM
 */
public class GWTHasValueBinder<T> extends AbstractUIBinder<HasValue<T>, T> {

  public static class SupportedEditors<T> implements IBinderMetaData<HasValue<T>, T>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{RadioButton.class, DateBox.class, DatePicker.class, SuggestBox.class, 
      		TextArea.class,	PasswordTextBox.class, TextBox.class};
    }

    public ObjectFactory<UIBinder<HasValue<T>, T>> getFactory() {
      return new ObjectFactory<UIBinder<HasValue<T>, T>>(){

        public UIBinder<HasValue<T>, T> getObject() {
          return new GWTHasValueBinder<T>();
        }};
    }
  }

	@Override
	protected void doInit(HasValue<T> widget, ModelValue<T> value) {
		widget.addValueChangeHandler(new ValueChangeHandler<T>(){

			public void onValueChange(ValueChangeEvent<T> event) {
				setEditorToValue(event.getValue());
			}});
	}

	@Override
	protected void setValueToEditor(T value, HasValue<T> widget) {
		widget.setValue(value);
	}

}
