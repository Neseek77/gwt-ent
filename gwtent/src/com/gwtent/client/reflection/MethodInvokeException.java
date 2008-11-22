package com.gwtent.client.reflection;

import com.gwtent.client.CheckedExceptionWrapper;

public class MethodInvokeException extends CheckedExceptionWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MethodInvokeException(String message) {
		super(message);
	}
	
	public MethodInvokeException(String message, Throwable cause) {
    super(message, cause);
	}
	
	public MethodInvokeException(Throwable cause) {
    super(cause);
	}

}
