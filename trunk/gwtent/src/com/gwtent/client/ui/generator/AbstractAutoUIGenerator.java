package com.gwtent.client.ui.generator;

import com.google.gwt.user.client.ui.Composite;
import com.gwtent.client.ui.editorFactory.EditorFactory;
import com.gwtent.client.ui.model.Fields;


public abstract class AbstractAutoUIGenerator implements UIGenerator {
	
	protected abstract EditorFactory getEditorFactory();

	
	public Composite generator(Fields fields){
		CompositeBase composite = new CompositeBase(fields, getEditorFactory());
		return composite;
	}
}
