package com.gwtent.client.aop;

import com.gwtent.client.CheckedExceptionWrapper;

public class AspectException extends CheckedExceptionWrapper {

	/**
	 * Constructor for AspectException.
	 * @param e
	 */
	public AspectException(Throwable e) {
		super(e);
	}

}
