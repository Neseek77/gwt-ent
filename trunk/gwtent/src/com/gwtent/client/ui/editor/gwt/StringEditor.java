package com.gwtent.client.ui.editor.gwt;

import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.editor.AbstractEditor;
import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.editorFactory.EditorCreator;
import com.gwtent.client.ui.model.Value;
import com.gwtent.client.ui.validate.ValidateException;

public class StringEditor extends AbstractEditor {
	
	public static class StringEditorCreator implements EditorCreator{

		public Editor createEditor(Value value) {
			return new StringEditor(value);
		}
		
	}

	private TextBox textBox = new TextBox();
	
	public StringEditor(Value value){
		super(value);
		
		if (value.getValue() != null)
			textBox.setText(value.getValue().toString());
		
		textBox.setReadOnly(this.getValue().getReadOnly());
		textBox.addFocusListener(new EditFocusListener());
		textBox.addKeyboardListener(new EditKeyboardListener());
	}
	
	public Widget getWidget() {
		return textBox;
	}
	
	protected void doValidate(){
		getValue().setValue(textBox.getText());
		
		try {
			this.getValue().getValidate().SyncValidate(textBox.getText());
			this.getValidateCallBack().finishValidate(null);
		} catch (ValidateException e) {
			this.getValidateCallBack().finishValidate(e);
		}
		
		try {
			this.getValue().getValidate().AsyncValidate(textBox.getText(), this.getValidateCallBack());
		} catch (ValidateException e) {
			this.getValidateCallBack().finishValidate(e);
		}
	}
	
	class EditFocusListener implements FocusListener {

		public void onFocus(Widget sender) {
			
		}

		public void onLostFocus(Widget sender) {
			doValidate();
		}
	}
	
	class EditKeyboardListener implements KeyboardListener{

		public void onKeyDown(Widget sender, char keyCode, int modifiers) {
			if (keyCode == KEY_ENTER){
				doValidate();
			}
		}

		public void onKeyPress(Widget sender, char keyCode, int modifiers) {
		}

		public void onKeyUp(Widget sender, char keyCode, int modifiers) {
		}
		
	}
	
	
	
}
