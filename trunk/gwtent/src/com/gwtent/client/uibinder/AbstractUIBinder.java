package com.gwtent.client.uibinder;


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
  
  public void binder(T widget, ModelValue<D> value) {
    this.widget = widget;
    this.modelValue = value;
    
    doInit(widget, value);
    
    value.addValueChangedListener(new ValueChangedListener());
    
    //doValueChanged();
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
  
  public T getWidget() {
    return widget;
  }

  public ModelValue<D> getModelValue() {
    return modelValue;
  }
}
