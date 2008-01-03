package com.gwtent.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface UserLoginRpc extends RemoteService {

	public Boolean login(String username, String password, boolean rememberMe);
	public void logout(String username);
	public String getCurrentUserName();
	
	/**
	 * Utility class for simplifing access to the instance of async service.
	 */
	public static class Util {
		private static UserLoginRpcAsync instance;

		public static UserLoginRpcAsync getInstance() {
			if (instance == null) {
				UserLoginRpcAsync service = (UserLoginRpcAsync) GWT.create(UserLoginRpc.class);
				ServiceDefTarget endpoint = (ServiceDefTarget) service;
				String moduleRelativeURL = GWT.getModuleBaseURL();
				moduleRelativeURL += "../userLogin.rpc";
				endpoint.setServiceEntryPoint(moduleRelativeURL);				
				return service;
			}
			return instance;
		}
	}
	
}
