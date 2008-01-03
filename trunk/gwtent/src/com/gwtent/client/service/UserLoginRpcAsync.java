package com.gwtent.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserLoginRpcAsync {

	public void login(String username, String password, boolean rememberMe, AsyncCallback callback);
	public void logout(String username, AsyncCallback callback);
	public void getCurrentUserName(AsyncCallback callback);
	
}
