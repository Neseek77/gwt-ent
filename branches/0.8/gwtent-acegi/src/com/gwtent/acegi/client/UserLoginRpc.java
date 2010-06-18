/*
 * GwtEnt - Gwt ent library.
 * 
 * Copyright (c) 2007, James Luo(JamesLuo.au@gmail.com)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.gwtent.acegi.client;

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
				instance = service;
			}
			return instance;
		}
	}
	
}
