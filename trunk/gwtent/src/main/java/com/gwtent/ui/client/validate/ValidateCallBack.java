/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


package com.gwtent.ui.client.validate;

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
