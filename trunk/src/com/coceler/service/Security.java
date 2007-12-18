package com.coceler.service;

/**
 * 提供安全处理,包含login, logout
 * @author James Luo
 * 2007-12-9 下午07:19:03
 *
 */
public interface Security {
	boolean login(String username, String password, boolean rememberMe);
	void logout(String username);
}
