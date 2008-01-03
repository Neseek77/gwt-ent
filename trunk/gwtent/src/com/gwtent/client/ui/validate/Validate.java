package com.gwtent.client.ui.validate;

public interface Validate {
	public void SyncValidate(Object value) throws ValidateException;
	public void AsyncValidate(Object value, ValidateCallBack callBack) throws ValidateException;
}
