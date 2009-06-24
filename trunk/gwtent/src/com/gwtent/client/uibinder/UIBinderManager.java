package com.gwtent.client.uibinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.user.client.ui.UIObject;
import com.gwtent.client.CheckedExceptionWrapper;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.modelvalue.ModelValueGWTImpl;
import com.gwtent.client.uibinder.modelvalue.ModelValueImpl;

public class UIBinderManager {
  
  private Map<UIBinder, ModelValue> binders = new HashMap<UIBinder, ModelValue>();
  private List<Object> uiObjectList = new ArrayList<Object>();
  
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
      ModelRootAccessor valueAccessor, boolean autoValidate, Class<?>... groups){
  	try {
  		uiObjectList.add(uiObject);
      UIBinder binder = UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinder(uiObject.getClass());
      ModelValue value = UIBinderGWTFactory.getModelValue(modelClass, path, readOnly, valueAccessor);
      binder.binder(uiObject, value, autoValidate, groups);
      binders.put(binder, value);
		} catch (Exception e) {
			throw new CheckedExceptionWrapper(e.getMessage() + "\n Path:" + path + " UIObject:" + uiObject + " ModelClass:" + modelClass, e);
		}
  }
  
  public Iterable<Object> getAllUIObjects(){
  	return uiObjectList;
  }
  
  /**
   * Notice to Binder Manager that model changed by code
   * 
   * @param pathPrefix..., the list of prefix of path, 
   * if "" or null means all model changed, 
   * "a.b" means all path start with "a.b" will be noticed.
   * 
   */
  public void modelChanged(String... pathPrefixs){
    for (ModelValue value : binders.values()){
    	if (isMatchPrefix(value, pathPrefixs))
    		(value).doValueChanged();
    }
  }
  
  private boolean isMatchPrefix(ModelValue value, String... pathPrefixs){
  	if (pathPrefixs == null || pathPrefixs.length <= 0)
  		return true;
  	
  	for (String pathPrefix : pathPrefixs){
  		if (value.getPropertyPath().startsWith(pathPrefix))
  			return true;
  	}
  	
  	return false;
  }
  
  /**
   * 
   * @param pathPrefixs, the Prefix of path, if null, all path match
   * @param showMessagesToUI
   * @param validateGroups
   * @return
   */
  public Set<ConstraintViolation<Object>> validate(String[] pathPrefixs, boolean showMessagesToUI, Class<?>... validateGroups){
  	Set<ConstraintViolation<Object>> result = new HashSet<ConstraintViolation<Object>>();
  	for (UIBinder binder : binders.keySet()){
  		ModelValue value = binders.get(binder);
  		
  		if (isMatchPrefix(value, pathPrefixs)){
  			Set<ConstraintViolation<Object>> r = binder.validate(showMessagesToUI, validateGroups);
    		if (r != null)
    			result.addAll(r);
  		}
    }
  	return result;
  }
  
  public Set<ConstraintViolation<Object>> validate(boolean showMessagesToUI, Class<?>... validateGroups){
  	Set<ConstraintViolation<Object>> result = new HashSet<ConstraintViolation<Object>>();
  	for (UIBinder binder : binders.keySet()){
  		Set<ConstraintViolation<Object>> r = binder.validate(showMessagesToUI, validateGroups);
  		if (r != null)
  			result.addAll(r);
  	}
  	return result;
  }
  
  public Set<ConstraintViolation<Object>> validate(UIObject widget, boolean showMessagesToUI, Class<?>... validateGroups){
  	for (UIBinder binder : binders.keySet()){
  		if (binder.getWidget() == widget){
  			return binder.validate(showMessagesToUI, validateGroups);
  		}
  	}
  	
  	return null;
  }
  
  
  public void hideAllValidateMessages(){
  	for (UIBinder binder : binders.keySet()){
  		binder.hideValidateMessageBox();
  	}
  }
}
