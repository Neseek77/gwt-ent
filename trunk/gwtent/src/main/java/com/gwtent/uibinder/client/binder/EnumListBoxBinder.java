package com.gwtent.uibinder.client.binder;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.common.client.ObjectFactory;
import com.gwtent.uibinder.client.AbstractUIBinder;
import com.gwtent.uibinder.client.IBinderMetaData;
import com.gwtent.uibinder.client.ModelValue;
import com.gwtent.uibinder.client.UIBinder;
import com.gwtent.widget.client.EnumListBox;

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
		if (value != null){
			for (int i = 0; i < widget.getItemCount(); i++){
				if (widget.getValue(i).equals(value.name())){
					widget.setSelectedIndex(i);
					return;
				}
			}
		}
		
		widget.setSelectedIndex(-1);
	}
}
