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

public class ExpressionProcessorList implements Validate {

	private List list = new ArrayList();
	
	public void AsyncValidate(Object value, ValidateCallBack callBack) throws ValidateException {
		Iterator iterator = list.iterator();
		
		StringBuffer exceptionMsg = new StringBuffer();
		
		while (iterator.hasNext()) {
			ProcessorHelper processorHelper = (ProcessorHelper) iterator.next();
			
			try {
				processorHelper.processor.AsyncValidate(processorHelper.expression, value, callBack);
			} catch (ValidateException e) {
				exceptionMsg.append(e.getMessage() + "\n");
			}
		}
		
		if (exceptionMsg.length() > 0)
			throw new ValidateException(exceptionMsg.toString());
		
	}

	public void SyncValidate(Object value) throws ValidateException {
		Iterator iterator = list.iterator();
		
		StringBuffer exceptionMsg = new StringBuffer();
		
		while (iterator.hasNext()) {
			ProcessorHelper processorHelper = (ProcessorHelper) iterator.next();
			
			try {
				processorHelper.processor.SyncValidate(processorHelper.expression, value);
			} catch (ValidateException e) {
				exceptionMsg.append(e.getMessage() + "<br>");
			}
		}
		
		if (exceptionMsg.length() > 0)
			throw new ValidateException(exceptionMsg.toString());
		
	}
	
	public void addProcessor(String expression, ExpressionProcessor processor){
		list.add(new ProcessorHelper(expression, processor));
	}
	
	class ProcessorHelper{
		public ProcessorHelper(String expression, ExpressionProcessor processor){
			this.expression = expression;
			this.processor = processor;
		}
		
		public String expression;
		public ExpressionProcessor processor;
	} 
	
}
