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
