package com.gwtent.client.uibinder.gxt;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;
import com.gwtent.client.uibinder.gxt.widget.EnumCombo;

public class EnumComboBinder<T extends Enum<T>> extends AbstractUIBinder<EnumCombo<T>, T> {

	public static class BinderMetaData<T extends Enum<T>> implements IBinderMetaData<EnumCombo<T>, T>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{EnumCombo.class};
    }

    public ObjectFactory<UIBinder<EnumCombo<T>, T>> getFactory() {
      return new ObjectFactory<UIBinder<EnumCombo<T>, T>>(){

        public UIBinder<EnumCombo<T>, T> getObject() {
          return new EnumComboBinder<T>();
        }};
    }

    public Class<?>[] getSupportedValueTypes() {
      return new Class<?>[]{};
    }
  }
	
	protected void doInit(EnumCombo<T> widget, ModelValue<T> value) {
		widget.updateComboSelectList((Class<T>) value.getValueClass());
		
		widget.addListener(Events.Valid, new Listener<FieldEvent>(){

      public void handleEvent(FieldEvent be) {
      	if (getWidget().getSelection().size() > 0){
      		SimpleComboValue<T> value = getWidget().getSelection().get(0);
    			getModelValue().setValue(value.getValue());
      		return;
      	}
      	
      	getModelValue().setValue(null);
      }});
	}

	@Override
	protected void setValueToEditor(T value, EnumCombo<T> widget) {
		List<SimpleComboValue<T>> values = new ArrayList<SimpleComboValue<T>>();
		
		for (int i = 0; i < widget.getStore().getCount(); i++){
			SimpleComboValue<T> widgetValue = widget.getStore().getAt(i);
			if (widgetValue.getValue() == value){
				values.add(widgetValue);
			}
		}
		
		widget.setSelection(values);
	}


}
