package com.gwtent.uibinder.client.binder;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.common.client.ObjectFactory;
import com.gwtent.uibinder.client.AbstractUIBinder;
import com.gwtent.uibinder.client.IBinderMetaData;
import com.gwtent.uibinder.client.ModelValue;
import com.gwtent.uibinder.client.UIBinder;
import com.gwtent.widget.client.RadioBoolSelector;
import com.gwtent.widget.client.RadioSelector;
import com.gwtent.widget.client.ValueChangedListener;

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
