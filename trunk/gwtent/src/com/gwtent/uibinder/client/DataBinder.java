package com.gwtent.uibinder.client;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.user.client.ui.UIObject;

/**
 * 
 * A Data Binder
 * 
 * <p>provide date bind and validate functions.
 * <p>Before using data bind, please register the binder first, the registration process tell data bind system 
 * how to bind a UIObject and a model object.
 * 
 * <P> O is the Owner of this data binder
 * 
 * @author James Luo
 *
 * 15/04/2010 10:00:46 AM
 */
public interface DataBinder<O> {
	
	/**
	 * All fields which is annotated by "@UIBind" will be bind to it's model
	 * <p>this is the main function you need for start up the bind system
	 * <p>this will not assign your model data to UI editors until you call "modelChanged()",
	 * you can call bindAll(O owner, boolean updateModelToUI);
	 */
	public void bindAll(final O owner);
	
	/**
	 * All fields which is annotated by "@UIBind" will be bind to it's model
	 * <p>this is the main function you need for start up the bind system
	 * <p>this will assign your model data to UI editors;
	 */
	public void bindAll(final O owner, final boolean updateModelToUI);

	/**
	 * To make the final js as small as possible
	 * I don't want the UI get involved into Reflection system
	 * That's why we need ModelValueAccessor to help us
	 * Get or Set values to model.
	 * <p>
	 * However with reflection we can do it automatically, but this will make js too big
	 * <p>
	 * @param <T>
	 * @param uiObject
	 * @param path
	 * @param readOnly
	 * @param modelClass
	 * @param valueAccessor
	 */
	public <T extends Object> void addBinder(T uiObject, String path,
			boolean readOnly, Class<?> modelClass, ModelRootAccessor valueAccessor,
			boolean autoValidate, Class<?>... groups);

	public Iterable<Object> getAllUIObjects();

	/**
	 * Notice to Binder Manager that model changed by code
	 * 
	 * @param pathPrefix..., the list of prefix of path, 
	 * if "" or null means all model changed, 
	 * "a.b" means all path start with "a.b" will be noticed.
	 * 
	 */
	public void modelChanged(String... pathPrefixs);

	/**
	 * 
	 * @param pathPrefixs, the Prefix of path, if null, all path match
	 * @param showMessagesToUI
	 * @param validateGroups
	 * @return
	 */
	public Set<ConstraintViolation<Object>> validate(String[] pathPrefixs,
			boolean showMessagesToUI, Class<?>... validateGroups);

	public Set<ConstraintViolation<Object>> validate(boolean showMessagesToUI,
			Class<?>... validateGroups);

	public Set<ConstraintViolation<Object>> validate(UIObject widget,
			boolean showMessagesToUI, Class<?>... validateGroups);

	public void hideAllValidateMessages();

	
	/**
	 * Add value changed listener to binding system. 
	 * <p>Every time the model changed by Editor, this listener will be got notification
	 * 
	 * @param listener
	 */
	public void addValueChangedByBindingListener(IValueChangedByBindingListener listener);
	
	public void removeValueChangedByBindingListener(IValueChangedByBindingListener listener);
	

}