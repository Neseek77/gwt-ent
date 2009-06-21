package com.gwtent.client.uibinder;

/**
 * 
 * @author JamesLuo@gmail.com
 *
 * @param <T> the editor type
 * @param <D> the data type which editor supposed
 */
public interface UIBinder<T, D> {
  public void binder(T widget, ModelValue<D> value, Class<?>... validateGroups);
}
