package com.gwtent.client.ui.generator;

import com.gwtent.client.ui.editorFactory.EditorFactory;
import com.gwtent.client.ui.editorFactory.ExtEditorFactory;

public class ExtAutoUIGenerator extends AbstractAutoUIGenerator {

	private EditorFactory editorFactory = new ExtEditorFactory();

	protected EditorFactory getEditorFactory() {
		return editorFactory;
	}

}
