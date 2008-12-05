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


package com.gwtent.client.ui.editor;

import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.model.Value;
import com.gwtent.client.ui.validate.ValidateCallBack;
import com.gwtent.client.ui.validate.ValidateException;

public abstract class AbstractEditor implements Editor {
	
	private Value value;
	private ValidateCallBack validateCallBack;

	public AbstractEditor(Value value){
		this.value = value;
	}
	
	public Value getValue() {
		return value;
	}

	public abstract Widget getWidget();
	public abstract Object getValueFromWidget();
	
	public void setValueFromWidget(){
		value.setValue(getValueFromWidget());
	}

	public void setValue(Value value) {
		this.value = value;
		
	}
	
	public ValidateCallBack getValidateCallBack(){
		return validateCallBack;
	}
	
	
	public void setValidateCallBack(ValidateCallBack callBack){
		this.validateCallBack = callBack;
	}
	
	protected void doValidate(){
		Object realValue = getValueFromWidget();
		setValueFromWidget();
		
		try {
			this.getValue().getValidate().SyncValidate(realValue);
			this.getValidateCallBack().finishValidate(null);
		} catch (ValidateException e) {
			this.getValidateCallBack().finishValidate(e);
		}
		
		try {
			this.getValue().getValidate().AsyncValidate(realValue, this.getValidateCallBack());
		} catch (ValidateException e) {
			this.getValidateCallBack().finishValidate(e);
		}
	}
	
	public class EditFocusListener implements FocusListener {

		public void onFocus(Widget sender) {
			
		}

		public void onLostFocus(Widget sender) {
			setValueFromWidget();
			doValidate();
		}
	}
	
	public class EditKeyboardListener implements KeyboardListener{

		public void onKeyDown(Widget sender, char keyCode, int modifiers) {
			if (keyCode == KEY_ENTER){
				setValueFromWidget();
				doValidate();
			}
		}

		public void onKeyPress(Widget sender, char keyCode, int modifiers) {
		}

		public void onKeyUp(Widget sender, char keyCode, int modifiers) {
		}
		
	}

}
