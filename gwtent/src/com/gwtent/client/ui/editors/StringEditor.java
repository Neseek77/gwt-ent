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
package com.gwtent.client.ui.editors;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.editor.AbstractEditor;
import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.editorFactory.EditorCreator;
import com.gwtent.client.ui.model.Value;

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
