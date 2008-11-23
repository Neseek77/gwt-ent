package com.gwtent.client.aop;

import com.gwtent.client.CheckedExceptionWrapper;

public class AspectException extends CheckedExceptionWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for AspectException.
	 * @param e
	 */
	public AspectException(Throwable e) {
		super(e);
	}

}
