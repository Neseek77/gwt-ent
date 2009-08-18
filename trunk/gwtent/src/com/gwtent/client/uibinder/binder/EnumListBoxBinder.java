package com.gwtent.client.uibinder.binder;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;
import com.gwtent.client.widget.EnumListBox;

public class EnumListBoxBinder<T extends Enum<T>> extends AbstractUIBinder<EnumListBox<T>, T> {
  
  public static class BinderMetaData<T extends Enum<T>> implements IBinderMetaData<EnumListBox<T>, T>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{EnumListBox.class};
    }

    public ObjectFactory<UIBinder<EnumListBox<T>, T>> getFactory() {
      return new ObjectFactory<UIBinder<EnumListBox<T>, T>>(){

        public UIBinder<EnumListBox<T>, T> getObject() {
          return new EnumListBoxBinder();
        }};
    }
  }
  

	@Override
	protected void doInit(EnumListBox<T> widget, ModelValue<T> value) {
		widget.updateComboSelectList((Class<T>) value.getValueClass());
		
		widget.addChangeListener(new ChangeListener(){
			public void onChange(Widget sender) {
				if (getWidget().getSelectedIndex() < 0)
					setEditorToValue(null);
				else{
					setEditorToValue(getWidget().getSelectedValue());
				}
			}});
	}


	@Override
	protected void setValueToEditor(T value, EnumListBox<T> widget) {
		for (int i = 0; i < widget.getItemCount(); i++){
			if (widget.getValue(i).equals(value.name())){
				widget.setSelectedIndex(i);
				return;
			}
		}
		widget.setSelectedIndex(-1);
	}
}
