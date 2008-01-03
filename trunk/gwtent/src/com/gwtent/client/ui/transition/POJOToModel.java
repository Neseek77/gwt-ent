package com.gwtent.client.ui.transition;

import com.gwtent.client.ui.model.Fields;

public interface POJOToModel {
	/**
	 * GWT cann't support pojo.getClass, so this function 
	 * need a Class param
	 * @param pojo
	 * @param clasz
	 * @return
	 * @throws TransitionException
	 */
	public Fields createModel(Object pojo, Class clasz) throws TransitionException;
}
