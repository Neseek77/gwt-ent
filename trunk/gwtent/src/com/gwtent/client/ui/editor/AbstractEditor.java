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
