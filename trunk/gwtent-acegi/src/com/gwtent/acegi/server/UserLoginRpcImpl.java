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
package com.gwtent.acegi.server;

import org.springframework.security.context.SecurityContextHolder;

import com.gwtent.acegi.client.UserLoginRpc;
import com.gwtent.service.Security;

public class UserLoginRpcImpl implements UserLoginRpc {
	
	private Security security;

	public Boolean login(String username, String password, boolean rememberMe) {
		return security.login(username, password, rememberMe);
	}

	public void logout(String username) {
		security.logout(username);
	}

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	public String getCurrentUserName() {
		if (SecurityContextHolder.getContext().getAuthentication() != null){
			return SecurityContextHolder.getContext().getAuthentication().getName();
		  //SecurityContextHolder.getContext().getAuthentication().getPrincipal()
		}else{
			return null;
		}
	}

}
