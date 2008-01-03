package com.gwtent.client.ui.validate;

/**
 * used for async call, when value finish call validate from server
 * call this back to editor
 * 
 * @author James Luo
 * 2007-12-27 下午03:20:15
 *
 */
public interface ValidateCallBack {
	/**
	 * if validate ok, exception should be null
	 * other side, use exception to get more infomation
	 * 
	 * @param exception
	 */
	public void finishValidate(ValidateException exception);
}
