package com.gwtent.client.uibinder;

public interface IValueChangedByBindingListener {
	/**
	 * Invoke before value changed by binding system
	 * @param value
	 * @return false will stop value continue write to model object
	 */
  public boolean beforeValueChange(Object instance, String property, Object value);
  
  public void afterValueChanged(Object instance, String property, Object value);
}
