package com.gwtent.client.uibinder.gxt;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;

public class SimpleComboBoxBinder<D> extends AbstractUIBinder<SimpleComboBox<D>, D> {
	public static class BinderMetaData<D> implements IBinderMetaData<SimpleComboBox<D>, D>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{SimpleComboBox.class};
    }

    public ObjectFactory<UIBinder<SimpleComboBox<D>, D>> getFactory() {
      return new ObjectFactory<UIBinder<SimpleComboBox<D>, D>>(){

        public UIBinder<SimpleComboBox<D>, D> getObject() {
          return new SimpleComboBoxBinder<D>();
        }};
    }

    public Class<?>[] getSupportedValueTypes() {
      return new Class<?>[]{};
    }
  }
	
	protected void doSetValueToModel(){
  	getModelValue().setValue(getWidget().getSimpleValue());
  }

	protected void doInit(SimpleComboBox<D> widget, ModelValue<D> value) {
		widget.addListener(Events.Valid, new Listener<FieldEvent>(){

      public void handleEvent(FieldEvent be) {
      	doSetValueToModel();
      }});
	}

	protected void setValueToEditor(D value, SimpleComboBox<D> widget) {
		widget.clearSelections();
		
		if (value == null)
			widget.setValue(null);
		else
  		widget.setSimpleValue(value);
	}
}
