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
