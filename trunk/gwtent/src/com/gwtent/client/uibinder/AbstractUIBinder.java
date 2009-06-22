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
  	private Set<ConstraintViolation<Object>> scv = null;
  	private boolean showMessagesToUI = true;
  	
  	private ErrorMessagePanel getMsgPanel(){
  		if (msgPanel == null)
  			msgPanel = new ErrorMessagePanel();
  		
  		return msgPanel;
  	}
  	
  	public ValidateValueChangedByBindingListener(){
  		
  	}
  	
		public void afterValueChanged(Object instance, String property, Object value) {
			
		}
		
		public Set<ConstraintViolation<Object>> getValidateResult(){
			return scv;
		}

		public boolean beforeValueChange(Object instance, String property, Object value) {
			if (msgPanel != null){
				getMsgPanel().clearErrorMsgs();
				getMsgPanel().hide();
			}
			
			scv = ValidatorFactory.getGWTValidator().validateValue((Class<Object>)instance.getClass(), property, value, validateGroups);
			if (scv.size() > 0){
				if (showMessagesToUI){
					for (ConstraintViolation<Object> cv : scv){
						getMsgPanel().addErrorMsg(cv.getMessage());
					}
					
					if (getWidget() instanceof UIObject)
						getMsgPanel().showPanel((UIObject)getWidget());
				}
				
				return false;
			}else{
				return true;
			}			
			
		}

		public void setShowMessagesToUI(boolean showMessagesToUI) {
			this.showMessagesToUI = showMessagesToUI;
		}

		public boolean isShowMessagesToUI() {
			return showMessagesToUI;
		}
  }
  
  public void binder(T widget, ModelValue<D> value, boolean autoValidate, Class<?>... validateGroups) {
    this.widget = widget;
    this.modelValue = value;
    
    this.validateGroups = validateGroups;
    
    doInit(widget, value);
    
    value.addValueChangedListener(new ValueChangedListener());
    
    if (autoValidate){
    	validateListener = new ValidateValueChangedByBindingListener();
    	value.addValueChangedByBindingListener(validateListener);
    }
    	
  }
  
  public Set<ConstraintViolation<Object>> validate(boolean showMessagesToUI, Class<?>... validateGroups){
  	if (validateListener != null)
  		validateListener.setShowMessagesToUI(showMessagesToUI);
  	
  	setEditorValueToValue();
  	if (validateListener != null){
  		validateListener.setShowMessagesToUI(true);
  		return validateListener.getValidateResult();
  	}
  	
  	return null;
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
  
  /**
   * Get the value from editor and put it to inner Value object
   * Some times this function will be called by straight way(for now i.e ValidateProcess)
   * 
   */
  protected abstract void setEditorValueToValue();


  private ValidateValueChangedByBindingListener validateListener;
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
