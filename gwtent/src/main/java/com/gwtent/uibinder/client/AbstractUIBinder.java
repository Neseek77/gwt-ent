package com.gwtent.uibinder.client;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.UIObject;
import com.gwtent.validate.client.ValidatorFactory;
import com.gwtent.validate.client.ui.ErrorMessagePanel;


/**
 * The abstract UIBinder.
 * 
 * <P>You can inherited from this class to create a new binder
 * 
 * @see com.gwtent.uibinder.client.binder.CheckBoxBinder to learn how to create a binder
 * 
 * @author JamesLuo@gmail.com
 *
 * @param <T> the editor type, for example "TextBox"
 * @param <D> the data type which editor supposed, for example "String"
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
  
  public class ValueChangedOutSideListener implements IValueChangedOutSideListener{

    public void valueChanged() {
      doValueChanged();
    }
  }
  
  public static class EditorToValueSetException extends RuntimeException{

		private static final long serialVersionUID = 1L;

		public EditorToValueSetException(String message) {
			super(message);
		}
		
		public EditorToValueSetException(String message, Throwable cause) {
      super(message, cause);
  }
  }
  
  private ErrorMessagePanel msgPanel;
  
  private ErrorMessagePanel getMsgPanel(){
		if (msgPanel == null){
			if (getWidget() instanceof UIObject)
				msgPanel = new ErrorMessagePanel();
		}
			
		
		return msgPanel;
	}
  
  private Set<ConstraintViolation<Object>> doValidate(boolean showMessagesToUI, D value, Class<?>... validateGroups){
  	if (msgPanel != null){
			getMsgPanel().clearErrorMsgs();
			getMsgPanel().hide();
		}
  	
  	Set<ConstraintViolation<Object>> scv = ValidatorFactory.getGWTValidator().validateValue((Class<Object>)getModelValue().getRootClass(), getModelValue().getPropertyPath(), value, validateGroups);
		if (scv.size() > 0){
			if (showMessagesToUI && isableToShowValidateMessage){
				for (ConstraintViolation<Object> cv : scv){
					getMsgPanel().addErrorMsg(cv.getMessage());
				}
				
				getMsgPanel().showPanel(((UIObject)getWidget()).getElement());
			}
		}
		
		return scv;
  }
  
  
  public void binder(T widget, ModelValue<D> value, boolean autoValidate, Class<?>... validateGroups) {
    this.widget = widget;
    this.modelValue = value;
    this.autoValidate = autoValidate;
    
    this.autoValidateGroups = validateGroups;
    
    isableToShowValidateMessage = (widget instanceof UIObject);
    	
    
    doInit(widget, value);
    
    value.addValueChangedListener(new ValueChangedOutSideListener());    	
  }
  
  public Set<ConstraintViolation<Object>> validate(boolean showMessagesToUI, Class<?>... validateGroups){
  	return this.doValidate(showMessagesToUI, getModelValue().getValue(), validateGroups);
  }
  
  protected void doValueChanged(){
  	settingValue = true;
  	
    try {
			setValueToEditor(modelValue.getValue(), widget);
		} finally {
			settingValue = false;
		}
  }
  
  /**
   * Connect value to widget here,
   * for example: listening to widget for any changes
   * <p>The is the place you binder your widget
   * @param widget
   * @param value
   */
  protected abstract void doInit(final T widget, final ModelValue<D> value);
  
  /**
   * this function will be called by doValueChanged()
   * it's mean every time model changed by code(not by user)
   * this function will be called
   * 
   * <P>Before this function be called, isSettingValue will set to True,
   * after finish this, isSettingValue will set to False.</P> 
   * 
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
   * 
   * if not pass validation or for some reason, the value can't write to target, return false,
   * The exception will be throw
   */
  protected boolean setEditorToValue(D value) throws EditorToValueSetException{
  	if (this.isSettingValue())
  		return false;   //is setting value to Editor, so direct return
  	
  	if (autoValidate && isAutoValidatable()){  //auto validate and validate failed, direct return
  		if (doValidate(true, value, autoValidateGroups).size() > 0){
  			//doValueChanged();  //For now, Just leave it, before submit to server, need call validate again
  			//return false;
  			//throw new EditorToValueSetException("Validate error.");
  		}
  	}
  	boolean s = false;
  	try {
			s = modelValue.setValue(value);
			if (s)
				return true;
		} catch (Exception e) {
			doValueChanged();
			GWT.log("Exception happended when set value to model object, the value had rolback", e);
			return false;
			//throw new EditorToValueSetException(e.getMessage(), e);
		}
		
  	doValueChanged();
  	return false;
  }
  
  /**
   * Override this function if you dont autoValidate worked.
   * For example, you don't autoValidate worked when your editor is disabled.
   * @return
   */
  protected boolean isAutoValidatable(){
  	return true;
  }


  //sometimes the bind editor don't have an element? not inherited UIObject?
  private boolean isableToShowValidateMessage;
  
  private boolean autoValidate;
  private Class<?>[] autoValidateGroups;
  
  private T widget;
  private ModelValue<D> modelValue;
  
  private boolean settingValue;
  
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
	
	public void hideValidateMessageBox(){
		if (msgPanel != null)
			this.getMsgPanel().hide();
	}

	public boolean isIsableToShowValidateMessage() {
		return isableToShowValidateMessage;
	}

	public void setSettingValue(boolean isSettingValue) {
		this.settingValue = isSettingValue;
	}

	public boolean isSettingValue() {
		return settingValue;
	}
}
