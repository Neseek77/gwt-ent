package com.gwtent.client.uibinder;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.user.client.ui.UIObject;
import com.gwtent.client.validate.ValidatorFactory;
import com.gwtent.client.validate.ui.ErrorMessagePanel;


/**
 * 
 * @author JamesLuo@gmail.com
 *
 * @param <T> the editor type
 * @param <D> the data type which editor supposed
 */
public abstract class AbstractUIBinder<T, D> implements UIBinder<T, D> {

  public abstract static class BinderMetaData implements IBinderMetaData{

    /**
     * return all classes which this binder supported
     * i.e: TextBox.class, TextArea.class, PasswordTextBox.class
     */
    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{};
    }
  }
  
  public class ValueChangedListener implements IValueChangedListener{

    public void valueChanged() {
      doValueChanged();
    }
  }
  
  public class ValidateValueChangedByBindingListener implements IValueChangedByBindingListener{

  	private ErrorMessagePanel msgPanel;
  	
  	private ErrorMessagePanel getMsgPanel(){
  		if (msgPanel == null)
  			msgPanel = new ErrorMessagePanel();
  		
  		return msgPanel;
  	}
  	
  	public ValidateValueChangedByBindingListener(){
  		
  	}
  	
		public void afterValueChanged(Object instance, String property, Object value) {
			
		}

		public boolean beforeValueChange(Object instance, String property, Object value) {
			Set<ConstraintViolation<Object>> scv = ValidatorFactory.getGWTValidator().validateProperty(instance, property, validateGroups);
			if (scv.size() > 0){
				for (ConstraintViolation<Object> cv : scv){
					getMsgPanel().addErrorMsg(cv.getMessage());
				}
				
				if (getWidget() instanceof UIObject)
					getMsgPanel().showPanel((UIObject)getWidget());
				
				return false;
			}else{
				return true;
			}			
			
		}
  }
  
  public void binder(T widget, ModelValue<D> value, Class<?>... validateGroups) {
    this.widget = widget;
    this.modelValue = value;
    
    this.validateGroups = validateGroups;
    
    doInit(widget, value);
    
    value.addValueChangedListener(new ValueChangedListener());
    
    value.addValueChangedByBindingListener(new ValidateValueChangedByBindingListener());
  }
  
  protected void doValueChanged(){
    setValueToEditor(modelValue.getValue(), widget);
  }
  
  /**
   * Connect value to widget here,
   * i.e: listening to widget for any changes
   * @param widget
   * @param value
   */
  protected abstract void doInit(final T widget, final ModelValue<D> value);
  
  /**
   * this function will be called by doValueChanged()
   * it's mean every time model changed by code(not by user)
   * this function will be called
   * @param value, the value, maybe sometime it's null, please check it
   * @param widget the widget want display the value
   */
  protected abstract void setValueToEditor(D value, T widget);


  
  private T widget;
  private ModelValue<D> modelValue;
  private Class<?>[] validateGroups;
  
  public T getWidget() {
    return widget;
  }

  public ModelValue<D> getModelValue() {
    return modelValue;
  }
}
