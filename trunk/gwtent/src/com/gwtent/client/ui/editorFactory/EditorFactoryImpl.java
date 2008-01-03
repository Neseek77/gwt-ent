package com.gwtent.client.ui.editorFactory;

import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.model.Value;

public class EditorFactoryImpl implements EditorFactory{

	public Editor getEditor(Value value) {
		Editor result = null;
		EditorCreator creator = EditorRegister.getInstance().getEditorRegister(value.getTypeName());
		if (creator != null)
			result = creator.createEditor(value);
		else
			throw new EditorCreateException("Cann't found editor for type: " + value.getTypeName() + ", forget register editor?");
		
		return result;
	}

}
