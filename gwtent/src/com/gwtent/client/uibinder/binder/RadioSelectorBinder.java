package com.gwtent.client.uibinder.binder;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;
import com.gwtent.client.widget.RadioBoolSelector;
import com.gwtent.client.widget.RadioSelector;
import com.gwtent.client.widget.ValueChangedListener;

public class RadioSelectorBinder<D extends Object> extends AbstractUIBinder<RadioSelector<D>, D> {

	public static class BinderMetaData<D extends Object> implements IBinderMetaData<RadioSelector<D>, D>{

    public ObjectFactory<UIBinder<RadioSelector<D>, D>> getFactory() {
      return new ObjectFactory<UIBinder<RadioSelector<D>, D>>(){

        public UIBinder<RadioSelector<D>, D> getObject() {
          return new RadioSelectorBinder<D>();
        }};
    }

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{RadioSelector.class, RadioBoolSelector.class};
    }
  }
	
	@Override
	protected void doInit(RadioSelector<D> widget, ModelValue<D> value) {
		widget.addValueChangedListener(new ValueChangedListener<D>(){
			public void onValueChanged(Widget sender, D value) {
				setEditorToValue(value);
			}});
	}
	
	@Override
	protected void setValueToEditor(D value, RadioSelector<D> widget) {
		widget.setValue(value);
	}

}
