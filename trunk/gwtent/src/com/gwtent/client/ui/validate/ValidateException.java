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
package com.gwtent.client.ui.validate;

public class ValidateException extends RuntimeException {

	/**
	 * 
	 * James Luo 2007-12-26 上午10:40:27
	 */
	private static final long serialVersionUID = 1L;

	public ValidateException() {
		super();
	}

	/**
	 * Constructs a new runtime exception with the specified detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call
	 * to {@link #initCause}.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 */
	public ValidateException(String message) {
		super(message);
	}
}
