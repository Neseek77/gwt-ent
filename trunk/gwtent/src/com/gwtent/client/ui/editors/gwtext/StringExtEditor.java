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
package com.gwtent.client.ui.editors.gwtext;

import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.editorFactory.EditorCreator;
import com.gwtent.client.ui.model.Value;
import com.gwtext.client.widgets.form.TextField;

public class StringExtEditor extends AbstractExtFieldEditor {
	
	public static class StringEditorCreator implements EditorCreator{

		public Editor createEditor(Value value) {
			return new StringExtEditor(value);
		}
		
	}

	private TextField textField = new TextField();
	
	public StringExtEditor(Value value){
		super(value);
		
		//textField.setWidth("100%");
		
		if (value.getValue() != null)
			textField.setValue(value.getValue().toString());
		
		textField.setDisabled(this.getValue().getReadOnly());
		textField.addFieldListener(new FieldListenerImpl());
		
		getForm().add(textField);
		getForm().end();
		getForm().render();
	}
	

	public Object getValueFromWidget() {
		return textField.getText();
	}
	

}
