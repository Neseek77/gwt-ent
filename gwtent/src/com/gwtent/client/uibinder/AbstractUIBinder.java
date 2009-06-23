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
  
  private ErrorMessagePanel msgPanel;
  
  private ErrorMessagePanel getMsgPanel(){
		if (msgPanel == null)
			msgPanel = new ErrorMessagePanel();
		
		return msgPanel;
	}
  
  private Set<ConstraintViolation<Object>> doValidate(boolean showMessagesToUI, D value, Class<?>... validateGroups){
  	if (msgPanel != null){
			getMsgPanel().clearErrorMsgs();
			getMsgPanel().hide();
		}
		
  	Set<ConstraintViolation<Object>> scv = ValidatorFactory.getGWTValidator().validateValue((Class<Object>)getModelValue().getRootClass(), getModelValue().getPropertyPath(), value, validateGroups);
		if (scv.size() > 0){
			if (showMessagesToUI){
				for (ConstraintViolation<Object> cv : scv){
					getMsgPanel().addErrorMsg(cv.getMessage());
				}
				
				if (getWidget() instanceof UIObject)
					getMsgPanel().showPanel((UIObject)getWidget());
			}
		}
		
		return scv;
  }
  
  
  public void binder(T widget, ModelValue<D> value, boolean autoValidate, Class<?>... validateGroups) {
    this.widget = widget;
    this.modelValue = value;
    this.autoValidate = autoValidate;
    
    this.autoValidateGroups = validateGroups;
    
    doInit(widget, value);
    
    value.addValueChangedListener(new ValueChangedListener());    	
  }
  
  public Set<ConstraintViolation<Object>> validate(boolean showMessagesToUI, Class<?>... validateGroups){
  	return this.doValidate(showMessagesToUI, getModelValue().getValue(), validateGroups);
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
   * When value changed by Editor, please invoke this function to set the new value into inner ModelValue object
   * 
   * This function will do Validate(if need) before setup to it.
   * 
   * If not invoke this function, the validate function will be lost.
   */
  protected void setEditorToValue(D value){
  	if (autoValidate){  //auto validate and validate failed, direct return
  		if (doValidate(true, value, autoValidateGroups).size() > 0)
  			return;
  	}
  	
  	modelValue.setValue(value);
  }


  private boolean autoValidate;
  private Class<?>[] autoValidateGroups;
  
  private T widget;
  private ModelValue<D> modelValue;
  
  public T getWidget() {
    return widget;
  }

  public ModelValue<D> getModelValue() {
    return modelValue;
  }

	public void setAutoValidate(boolean autoValidate) {
		this.autoValidate = autoValidate;
	}

	public boolean isAutoValidate() {
		return autoValidate;
	}
}
