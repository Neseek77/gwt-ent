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
