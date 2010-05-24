package com.gwtent.uibinder.client.gxt;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;

/**
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 * @param <D> the data type you want edit by this editor
 */
public class CheckBoxBinder<D> extends AbstractUIBinder<Field<D>, D> {

  public static class BinderMetaData<D> implements IBinderMetaData<Field<D>, D>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{CheckBox.class};
    }

    public ObjectFactory<UIBinder<Field<D>, D>> getFactory() {
      return new ObjectFactory<UIBinder<Field<D>, D>>(){

        public UIBinder<Field<D>, D> getObject() {
          return new CheckBoxBinder<D>();
        }};
    }

    public Class<?>[] getSupportedValueTypes() {
      return new Class<?>[]{};
    }
  }
  
  protected boolean isAutoValidatable(){
  	return getWidget().isEnabled();
  }
  
  protected void doInit(Field<D> widget, ModelValue<D> value) {
    widget.addListener(Events.Change, new Listener<FieldEvent>(){

      public void handleEvent(FieldEvent be) {
				setEditorToValue(getWidget().getValue());
				
      }});
  }

  protected void setValueToEditor(D value, Field<D> widget) {
    widget.setValue(value);
  }

}
