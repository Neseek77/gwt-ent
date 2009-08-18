package com.gwtent.client.uibinder.binder;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;
import com.gwtent.client.uibinder.AbstractUIBinder.EditorToValueSetException;

public class ListBoxBinder extends AbstractUIBinder<ListBox, String> {
  
  public static class BinderMetaData implements IBinderMetaData<ListBox, String>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{ListBox.class};
    }

    public ObjectFactory<UIBinder<ListBox, String>> getFactory() {
      return new ObjectFactory<UIBinder<ListBox, String>>(){

        public UIBinder<ListBox, String> getObject() {
          return new ListBoxBinder();
        }};
    }
  }
  

	@Override
	protected void doInit(ListBox widget, ModelValue<String> value) {
		widget.addChangeListener(new ChangeListener(){
			public void onChange(Widget sender) {
				if (getWidget().getSelectedIndex() < 0)
					setEditorToValue("");
				else
					setEditorToValue(getWidget().getItemText(getWidget().getSelectedIndex()));
			}});
	}


	@Override
	protected void setValueToEditor(String value, ListBox widget) {
		for (int i = 0; i < widget.getItemCount(); i++){
			if (widget.getValue(i).equals(value)){
				widget.setSelectedIndex(i);
				return;
			}
		}
		widget.setSelectedIndex(-1);
	}
}
