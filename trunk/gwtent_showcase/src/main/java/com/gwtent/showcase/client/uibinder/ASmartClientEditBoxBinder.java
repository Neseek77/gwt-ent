package com.gwtent.showcase.client.uibinder;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.gwtent.common.client.ObjectFactory;
import com.gwtent.uibinder.client.AbstractUIBinder;
import com.gwtent.uibinder.client.IBinderMetaData;
import com.gwtent.uibinder.client.ModelValue;
import com.gwtent.uibinder.client.UIBinder;

public class ASmartClientEditBoxBinder extends AbstractUIBinder<ASmartClientEditBox, String> {
  
	/**
	 * A helper class to make your register process easier
	 */
  public static class SupportedEditors implements IBinderMetaData<ASmartClientEditBox, String>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{ASmartClientEditBox.class};
    }

    public ObjectFactory<UIBinder<ASmartClientEditBox, String>> getFactory() {
      return new ObjectFactory<UIBinder<ASmartClientEditBox, String>>(){

        public UIBinder<ASmartClientEditBox, String> getObject() {
          return new ASmartClientEditBoxBinder();
        }};
    }
  }
  

	@Override
	protected void doInit(ASmartClientEditBox widget, ModelValue<String> value) {
		//This is the place you connect a widget to a ModelValue object
		widget.addValueChangeHandler(new ValueChangeHandler<String>(){
			public void onValueChange(ValueChangeEvent<String> event) {
				setEditorToValue(event.getValue());
			}});
	}


	@Override
	protected void setValueToEditor(String value, ASmartClientEditBox widget) {
		// every time model changed by code(not by user) this function will be called
		// you need setup the new vaule to the widget.
		if (value != null)
			widget.setValue(value);
		else
			widget.setValue("");
	}
}
