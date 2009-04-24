package com.gwtent.client.uibinder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.modelvalue.ModelValueGWTImpl;
import com.gwtent.client.uibinder.modelvalue.ModelValueImpl;

public class UIBinderManager {
  
  private Map<UIBinder, ModelValue> binders = new HashMap<UIBinder, ModelValue>();
  
  public static interface ModelCallback{
    public Object getModel();
  }
  

  
  /**
   * @param uiObject the widget or any object you defined, ie: TextBox
   * @param path the path i.e: company.name, it's mean "model".company.name, can be null, if path is null, will bind model to uiObject
   * @param readOnly read only
   * @param modelRoot the root model
   * 
   *  Please NOTE: this function is used for the modelRoot itself never changed
   */
//  public void addBinder(Object uiObject, String path, boolean readOnly, final Object modelRoot){
//    addBinder(uiObject, path, readOnly, modelRoot.getClass(), new ModelRootAccessor(){
//
//      public Object getValue() {
//        return modelRoot;
//      }
//
//      public void setValue(Object value) {
//        //The model root can't be write by binder
//      }});
//  }
  
  /**
   * To make the final js as small as possible
   * I don't want the UI get involved into Reflection system
   * That's why we need ModelValueAccessor to help us
   * Get or Set values to model.
   * With reflection we can do it automatically
   * 
   * @param <T>
   * @param uiObject
   * @param path
   * @param readOnly
   * @param modelClass
   * @param valueAccessor
   */
  public <T extends Object> void addBinder(T uiObject, String path, boolean readOnly, Class<?> modelClass,
      ModelRootAccessor valueAccessor){
    UIBinder binder = UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinder(uiObject.getClass());
    ModelValue value = UIBinderGWTFactory.getModelValue(modelClass, path, readOnly, valueAccessor);
    binder.binder(uiObject, value);
    binders.put(binder, value);
  }
  
  /**
   * Notice to Binder Manager that model changed by code
   * 
   * @param pathPrefix..., the list of prefix of path, 
   * if "" or null means all model changed, 
   * "a.b" means all path start with "a.b" will be noticed
   * 
   */
  public void modelChanged(String... pathPrefixs){
    for (ModelValue value : binders.values()){
      (value).doValueChanged();
    }
  }
  
}
