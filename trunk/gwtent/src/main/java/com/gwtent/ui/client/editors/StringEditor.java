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


package com.gwtent.ui.client.editors;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.ui.client.editor.AbstractEditor;
import com.gwtent.ui.client.editor.Editor;
import com.gwtent.ui.client.editorFactory.EditorCreator;
import com.gwtent.ui.client.model.Value;

public class StringEditor extends AbstractEditor {
	
	public static class StringEditorCreator implements EditorCreator{

		public Editor createEditor(Value value) {
			return new StringEditor(value);
		}
		
	}

	private TextBox textBox = new TextBox();
	
	public StringEditor(Value value){
		super(value);
		
		textBox.setWidth("100%");
		
		if (value.getValue() != null)
			textBox.setText(value.getValue().toString());
		
		textBox.setReadOnly(this.getValue().getReadOnly());
		textBox.addFocusListener(new EditFocusListener());
		textBox.addKeyboardListener(new EditKeyboardListener());
	}
	
	public Widget getWidget() {
		return textBox;
	}

	public Object getValueFromWidget() {
		return textBox.getText();
	}
	

}
