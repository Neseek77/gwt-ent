package com.gwtent.client.validate.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidatorContext;

public class ConstraintValidatorContextImpl implements
		ConstraintValidatorContext {
	
	public static class ErrorMsg{
		private final String message;
		private final String property;
		
		public ErrorMsg(String message, String property){
			this.message = message;
			this.property = property;
		}
		
		public String getMessage() {
			return message;
		}
		
		/**
		 * if "" or Null, it's point to default property
		 * @return
		 */
		public String getProperty() {
			return property;
		}
	}
	
	private boolean defaultErrorDisabled = false;
	private final String defaultErrorMsg;
	private List<ErrorMsg> msgs = new ArrayList<ErrorMsg>();

	public ConstraintValidatorContextImpl(String defaultErrorMsg){
		this.defaultErrorMsg = defaultErrorMsg;
		msgs.add(new ErrorMsg(defaultErrorMsg, null));
	}

	public void addError(String message) {
		disableDefaultError();
		
		msgs.add(new ErrorMsg(message, null));
	}

	public void addError(String message, String property) {
		disableDefaultError();
		
		msgs.add(new ErrorMsg(message, property));
	}

	public void disableDefaultError() {
		if (!defaultErrorDisabled)  //if never disabled before, clear the default message
			msgs.clear();
		
		defaultErrorDisabled = true;
	}

	public String getDefaultErrorMessage() {
		return defaultErrorMsg;
	}

	public boolean isDefaultErrorDisabled() {
		return defaultErrorDisabled;
	}

	/**
	 * If not defaultErrorDisabled, this return the default message
	 * If defaultErrorDisabled, this return the added messages
	 * @return
	 */
	List<ErrorMsg> getMsgs() {
		return msgs;
	}

}
