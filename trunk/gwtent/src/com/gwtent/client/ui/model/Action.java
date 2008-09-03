package com.gwtent.client.ui.model;

public interface Action {
	public String getCaption();
	
	public void doAction(Object instance);
	public void doAsyncAction(Object instance, ActionCallBack callBack);
}
