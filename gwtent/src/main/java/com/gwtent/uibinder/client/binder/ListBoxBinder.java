package com.gwtent.uibinder.client.binder;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.common.client.ObjectFactory;
import com.gwtent.uibinder.client.AbstractUIBinder;
import com.gwtent.uibinder.client.IBinderMetaData;
import com.gwtent.uibinder.client.ModelValue;
import com.gwtent.uibinder.client.UIBinder;

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
