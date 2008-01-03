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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ValidateChainImpl implements ValidateChain {
	
	private List chains = new ArrayList();

	public void addValidate(Validate validate) {
		chains.add(validate);
	}

	public Iterator iterator() {
		return chains.iterator();
	}

	public void AsyncValidate(Object value, ValidateCallBack callBack)
			throws ValidateException {
		
		Iterator iterator = this.iterator();
		
		while (iterator.hasNext()){
			Validate validate = (Validate)iterator.next();
			
			validate.AsyncValidate(value, callBack);
		}

	}

	public void SyncValidate(Object value) throws ValidateException {
		Iterator iterator = this.iterator();
		
		while (iterator.hasNext()){
			Validate validate = (Validate)iterator.next();
			
			validate.SyncValidate(value);
		}

	}

}
