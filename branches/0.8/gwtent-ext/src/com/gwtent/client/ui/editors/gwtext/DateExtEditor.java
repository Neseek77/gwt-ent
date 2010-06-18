package com.gwtent.client.ui.editors.gwtext;

import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.editorFactory.EditorCreator;
import com.gwtent.client.ui.model.Value;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;

import java.util.Date;

public class DateExtEditor extends AbstractExtFieldEditor {
	
	public static class DateEditorCreator implements EditorCreator {

		public Editor createEditor(Value value) {
			return new DateExtEditor(value);
		}
	}
	
	private DateField dateField;
	

	public DateExtEditor(Value value) {
		super(value);
		
		dateField = new DateField();
		
		if (value.getValue() != null){
			dateField.setValue((Date)value.getValue());
		}
		
		dateField.setDisabled(value.getReadOnly());
		
		dateField.addListener(new FieldListenerImpl());
	}

	public Object getValueFromWidget() {
		return dateField.getValue();
	}	

	public void finishAddEditors(){
		//dateField.setWidth("600px");
	}

  @Override
  public Field getField() {
    return dateField;
  }
}
