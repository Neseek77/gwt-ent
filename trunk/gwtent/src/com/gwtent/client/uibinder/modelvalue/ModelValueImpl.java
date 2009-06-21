package com.gwtent.client.uibinder.modelvalue;

import java.util.HashSet;
import java.util.Set;

import com.gwtent.client.reflection.pathResolver.PathResolver;
import com.gwtent.client.uibinder.IValueChangedByBindingListener;
import com.gwtent.client.uibinder.IValueChangedListener;
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
  private Set<IValueChangedListener> listeners = new HashSet<IValueChangedListener>();
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

  public void setValue(Object value) {
  	if (doBeforeChangedByBinding(value)){
      rootAccessor.setValue(value);
      doAfterChangedByBinding(value);
  	}
  }

  public void doValueChanged() {
    for (IValueChangedListener listener : listeners){
      listener.valueChanged();
    }
  }

  public void addValueChangedListener(IValueChangedListener listener) {
    listeners.add(listener);    
  }

  public void removeValueChangedListener(IValueChangedListener listener) {
    listeners.remove(listener);
  }
  
  protected boolean doBeforeChangedByBinding(Object value){
  	boolean result = true;
  	for (IValueChangedByBindingListener listener : changedByBindingListeners){
  		if (! listener.beforeValueChange(value))
  			result = false;
  	}
  	
  	return result;
  }
  
  protected void doAfterChangedByBinding(Object value){
  	for (IValueChangedByBindingListener listener : changedByBindingListeners){
  		listener.afterValueChanged(value);
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
}