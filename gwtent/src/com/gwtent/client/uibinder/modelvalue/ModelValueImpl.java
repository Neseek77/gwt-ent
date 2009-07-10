package com.gwtent.client.uibinder.modelvalue;

import java.util.HashSet;
import java.util.Set;

import com.gwtent.client.reflection.pathResolver.PathResolver;
import com.gwtent.client.uibinder.IValueChangedByBindingListener;
import com.gwtent.client.uibinder.IValueChangedOutSideListener;
import com.gwtent.client.uibinder.ModelRootAccessor;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinderValidator;

/**
 * A normal implement of ModelValue
 * This is working with ModelRootAccessor
 * It's not support path
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
public class ModelValueImpl implements ModelValue<Object> {

  public ModelValueImpl(Class<?> rootClass, boolean readOnly, ModelRootAccessor rootAccessor){
  	this.rootClass = rootClass;
    this.readOnly = readOnly;
    this.rootAccessor = rootAccessor;
  }
  
  private final Class<?> rootClass;
  protected ModelRootAccessor rootAccessor;
  protected boolean readOnly;
  private Set<IValueChangedOutSideListener> listeners = new HashSet<IValueChangedOutSideListener>();
  private Set<IValueChangedByBindingListener> changedByBindingListeners = new HashSet<IValueChangedByBindingListener>();

  public String getAsString() {
    Object value = getValue();
    if (value != null)
      return value.toString();
    else
      return null;
  }

  public boolean getReadOnly() {
    return readOnly;
  }

  public UIBinderValidator getValidator() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getValue() {
    return rootAccessor.getValue();
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public boolean setValue(Object value) {
  	//if (doBeforeChangedByBinding(value)){
      rootAccessor.setValue(value);
      return true;
      //doAfterChangedByBinding(value);
  	//}
  }

  public void doValueChanged() {
    for (IValueChangedOutSideListener listener : listeners){
      listener.valueChanged();
    }
  }

  public void addValueChangedListener(IValueChangedOutSideListener listener) {
    listeners.add(listener);    
  }

  public void removeValueChangedListener(IValueChangedOutSideListener listener) {
    listeners.remove(listener);
  }
  
  /**
   * 
   * @param instance last level instance
   * @param property last level path
   * @param value the value
   * @return
   */
  protected boolean doBeforeChangedByBinding(Object instance, String property, Object value){
  	boolean result = true;
  	for (IValueChangedByBindingListener listener : changedByBindingListeners){
  		if (! listener.beforeValueChange(instance, property, value))
  			result = false;
  	}
  	
  	return result;
  }
  
  protected void doAfterChangedByBinding(Object instance, String property, Object value){
  	for (IValueChangedByBindingListener listener : changedByBindingListeners){
  		listener.afterValueChanged(instance, property, value);
  	}	
  }
  
  public void removeValueChangedByBindingListener(IValueChangedByBindingListener listener){
  	changedByBindingListeners.remove(listener);
  }
  
  public void addValueChangedByBindingListener(IValueChangedByBindingListener listener){
  	changedByBindingListeners.add(listener);
  }

	public Class<?> getValueClass() {
		return rootClass;
	}

	public Class<?> getRootClass() {
		return rootClass;
	}

	public String getPropertyPath() {
		return null;
	}
}