package com.gwtent.uibinder.client.gxt;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;
import com.gwtent.client.uibinder.AbstractUIBinder.EditorToValueSetException;

public class ComboBoxBinder<D extends ModelData> extends AbstractUIBinder<ComboBox<D>, D> {
	public static class BinderMetaData<D extends ModelData> implements IBinderMetaData<ComboBox<D>, D>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{ComboBox.class};
    }

    public ObjectFactory<UIBinder<ComboBox<D>, D>> getFactory() {
      return new ObjectFactory<UIBinder<ComboBox<D>, D>>(){

        public UIBinder<ComboBox<D>, D> getObject() {
          return new ComboBoxBinder<D>();
        }};
    }

    public Class<?>[] getSupportedValueTypes() {
      return new Class<?>[]{};
    }
  }

	protected void doInit(ComboBox<D> widget, ModelValue<D> value) {
		widget.addListener(Events.Valid, new Listener<FieldEvent>(){

      public void handleEvent(FieldEvent be) {
      	try {
      		if (getWidget().getView().getSelectionModel() != null && getWidget().getView().getSelectionModel().getSelectedItem() != null)
      			setEditorToValue(getWidget().getView().getSelectionModel().getSelectedItem());
      		else
      			setEditorToValue(null);
				} catch (EditorToValueSetException e) {
					doValueChanged();
				}
      }});
	}

	protected void setValueToEditor(D value, ComboBox<D> widget) {
		widget.clearSelections();
		
		if (value == null)
			widget.setValue(null);
		else
  		widget.setValue(value);
	}

}
