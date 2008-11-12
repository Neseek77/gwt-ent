package com.gwtent.client.reflection;

public interface Constructor extends AbstractMethod {
	/**
	 * New Instance
	 * For now just support create class that contains default Constructor.
	 * @return
	 */
	public Object newInstance();
}