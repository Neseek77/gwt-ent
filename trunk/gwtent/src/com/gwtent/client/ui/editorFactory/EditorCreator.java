package com.gwtent.client.ui.editorFactory;

import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.model.Value;

public interface EditorCreator {
	public Editor createEditor(Value value);
}
