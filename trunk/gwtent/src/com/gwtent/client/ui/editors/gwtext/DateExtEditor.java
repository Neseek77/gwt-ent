package com.gwtent.client.ui.editors.gwtext;

import java.util.Date;

import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.editorFactory.EditorCreator;
import com.gwtent.client.ui.model.Value;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.DateFieldConfig;

public class DateExtEditor extends AbstractExtFieldEditor {
	
	public static class DateEditorCreator implements EditorCreator {

		public Editor createEditor(Value value) {
			return new DateExtEditor(value);
		}
	}
	
	private DateField dateField;
	

	public DateExtEditor(Value value) {
		super(value);
		
		dateField = new DateField(new DateFieldConfig(){
			{
				//your config
				 setName("username");
	             setWidth("100%");
			}
		});
		
		if (value.getValue() != null){
			dateField.setValue((Date)value.getValue());
		}
		
		dateField.setDisabled(value.getReadOnly());
		
		getForm().add(dateField);
		dateField.addFieldListener(new FieldListenerImpl());
		getForm().end();
		getForm().render();
	}

	public Object getValueFromWidget() {
		return dateField.getValue();
	}	

}
