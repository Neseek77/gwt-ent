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

public abstract class UIBinderManager<O> implements IValueChangedByBindingListener, DataBinder<O>{
  
  private Map<UIBinder, ModelValue> binders = new HashMap<UIBinder, ModelValue>();
  private List<Object> uiObjectList = new ArrayList<Object>();
  
  public static interface ModelCallback{
    public Object getModel();
  }
  
  public void bindAll(final O owner, final boolean updateModelToUI){
  	bindAll(owner);
  	
  	if (updateModelToUI){
  		this.modelChanged();
  	}
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
  
  /* (non-Javadoc)
	 * @see com.gwtent.client.uibinder.DataBinder#addBinder(T, java.lang.String, boolean, java.lang.Class, com.gwtent.client.uibinder.ModelRootAccessor, boolean, java.lang.Class)
	 */
  public <T extends Object> void addBinder(T uiObject, String path, boolean readOnly, Class<?> modelClass,
      ModelRootAccessor valueAccessor, boolean autoValidate, Class<?>... groups){
  	try {
  		uiObjectList.add(uiObject);
      UIBinder binder = UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinder(uiObject.getClass());
      ModelValue value = UIBinderGWTFactory.getModelValue(modelClass, path, readOnly, valueAccessor);
      value.addValueChangedByBindingListener(this);
      binder.binder(uiObject, value, autoValidate, groups);
      binders.put(binder, value);
		} catch (Exception e) {
			throw new CheckedExceptionWrapper(e.getMessage() + "\n Path:" + path + " UIObject:" + uiObject + " ModelClass:" + modelClass, e);
		}
  }
  
  /* (non-Javadoc)
	 * @see com.gwtent.client.uibinder.DataBinder#getAllUIObjects()
	 */
  public Iterable<Object> getAllUIObjects(){
  	return uiObjectList;
  }
  
  /* (non-Javadoc)
	 * @see com.gwtent.client.uibinder.DataBinder#modelChanged(java.lang.String)
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
  		if (value.getPropertyFullPath().startsWith(pathPrefix))
  			return true;
  	}
  	
  	return false;
  }
  
  /* (non-Javadoc)
	 * @see com.gwtent.client.uibinder.DataBinder#validate(java.lang.String[], boolean, java.lang.Class)
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
  
  /* (non-Javadoc)
	 * @see com.gwtent.client.uibinder.DataBinder#validate(boolean, java.lang.Class)
	 */
  public Set<ConstraintViolation<Object>> validate(boolean showMessagesToUI, Class<?>... validateGroups){
  	Set<ConstraintViolation<Object>> result = new HashSet<ConstraintViolation<Object>>();
  	for (UIBinder binder : binders.keySet()){
  		Set<ConstraintViolation<Object>> r = binder.validate(showMessagesToUI, validateGroups);
  		if (r != null)
  			result.addAll(r);
  	}
  	return result;
  }
  
  /* (non-Javadoc)
	 * @see com.gwtent.client.uibinder.DataBinder#validate(com.google.gwt.user.client.ui.UIObject, boolean, java.lang.Class)
	 */
  public Set<ConstraintViolation<Object>> validate(UIObject widget, boolean showMessagesToUI, Class<?>... validateGroups){
  	for (UIBinder binder : binders.keySet()){
  		if (binder.getWidget() == widget){
  			return binder.validate(showMessagesToUI, validateGroups);
  		}
  	}
  	
  	return null;
  }
  
  
  /* (non-Javadoc)
	 * @see com.gwtent.client.uibinder.DataBinder#hideAllValidateMessages()
	 */
  public void hideAllValidateMessages(){
  	for (UIBinder binder : binders.keySet()){
  		binder.hideValidateMessageBox();
  	}
  }
  
  /* (non-Javadoc)
	 * @see com.gwtent.client.uibinder.DataBinder#removeValueChangedByBindingListener(com.gwtent.client.uibinder.IValueChangedByBindingListener)
	 */
  public void removeValueChangedByBindingListener(IValueChangedByBindingListener listener){
  	changedByBindingListeners.remove(listener);
  }
  
  /* (non-Javadoc)
	 * @see com.gwtent.client.uibinder.DataBinder#addValueChangedByBindingListener(com.gwtent.client.uibinder.IValueChangedByBindingListener)
	 */
  public void addValueChangedByBindingListener(IValueChangedByBindingListener listener){
  	changedByBindingListeners.add(listener);
  }
  
  private ValueChangedByBindingListenerCollection changedByBindingListeners = new ValueChangedByBindingListenerCollection();

	public void afterValueChanged(Object instance, String property, Object value) {
		changedByBindingListeners.fireAfterValueChanged(instance, property, value);
	}

	public boolean beforeValueChange(Object instance, String property,
			Object value) {
		return changedByBindingListeners.fireBeforeValueChange(instance, property, value);
	}

}
