package com.gwtent.service;

/**
 * 
 * @author James Luo
 * 2007-12-9 07:19:03
 *
 */
public interface Security {
	boolean login(String username, String password, boolean rememberMe);
	void logout(String username);
}
