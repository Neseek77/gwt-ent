package com.gwtent.client.ui.generator;

import com.gwtent.client.ui.editorFactory.EditorFactory;
import com.gwtent.client.ui.editorFactory.EditorFactoryImpl;

public class GwtAutoUIGenerator extends AbstractAutoUIGenerator {

	private EditorFactory editorFactory = new EditorFactoryImpl();

	protected EditorFactory getEditorFactory() {
		return editorFactory;
	}


}
