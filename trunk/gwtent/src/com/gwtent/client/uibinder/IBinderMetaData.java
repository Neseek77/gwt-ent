package com.gwtent.client.uibinder;

import com.gwtent.client.common.ObjectFactory;

public interface IBinderMetaData<T, D> {
  /**
   * Return all editor types this binder supported
   * i.e: TextBoxBinder support 
   * TextBox.class, TextArea.class, PasswordTextBox.class...
   * 
   * @return the array of supported editor classes
   */
  Class<?>[] getSupportedEditors();
  
  /**
   * Return all supported data types
   * i.e: TextBoxBinder support binder String, Integer, Boolean at the same time
   * @return
   */
  //Class<?>[] getSupportedValueTypes();
  
  /**
   * Object factory, create the Binder
   * @return
   */
  ObjectFactory<UIBinder<T, D>> getFactory();
}
