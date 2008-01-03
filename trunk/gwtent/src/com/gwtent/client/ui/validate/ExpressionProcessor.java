package com.gwtent.client.ui.validate;

public interface ExpressionProcessor{
	public boolean canProcess(String expression);
	
	public void SyncValidate(String expression, Object value) throws ValidateException;
	public void AsyncValidate(String expression, Object value, ValidateCallBack callBack) throws ValidateException;
}
