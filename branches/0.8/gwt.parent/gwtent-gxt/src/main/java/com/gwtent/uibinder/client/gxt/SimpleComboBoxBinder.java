package com.gwtent.uibinder.client.gxt;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.gwtent.common.client.ObjectFactory;
import com.gwtent.uibinder.client.AbstractUIBinder;
import com.gwtent.uibinder.client.IBinderMetaData;
import com.gwtent.uibinder.client.ModelValue;
import com.gwtent.uibinder.client.UIBinder;
import com.gwtent.uibinder.client.AbstractUIBinder.EditorToValueSetException;

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

	protected void doInit(SimpleComboBox<D> widget, ModelValue<D> value) {
		widget.addListener(Events.Valid, new Listener<FieldEvent>(){

      public void handleEvent(FieldEvent be) {
      	try {
					setEditorToValue(getWidget().getSimpleValue());
				} catch (EditorToValueSetException e) {
					doValueChanged();
				}
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
