package com.gwtent.client.ui.editorFactory;

import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.model.Value;

public interface EditorFactory {
	public Editor getEditor(Value value);
}
