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

public class StringValidateProcessor implements ExpressionProcessor {

	private static StringValidateProcessor processor;
	
	public static StringValidateProcessor getInstance(){
		if (processor == null)
			processor = new StringValidateProcessor();
		
		return processor;
	}
	
	public boolean canProcess(String expression) {
		if (expression.startsWith("StrLength")){
			return true;
		}else{
			return false;
		}
	}

	
	protected void validateLength(String expression, String value) throws ValidateException {
		if (expression.indexOf(">") > 0){
			String[] strs = expression.split(">");
			int length = (Integer.valueOf(strs[1])).intValue();
			
			if (value.length() <= length){
				throw new ValidateException("String length must great than " + length);
			}
		}
		
		if (expression.indexOf("<") > 0){
			String[] strs = expression.split("<");
			int length = (Integer.valueOf(strs[1])).intValue();
			
			if (value.length() >= length){
				throw new ValidateException("String length must less than " + length);
			}
		}
		
	}

	public void AsyncValidate(String expression, Object value, ValidateCallBack callBack) throws ValidateException {
		// TODO Auto-generated method stub
		
	}

	public void SyncValidate(String expression, Object value) throws ValidateException {
		validateLength(expression, value.toString());
		
	}

}
