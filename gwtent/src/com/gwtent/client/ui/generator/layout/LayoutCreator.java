package com.gwtent.client.ui.generator.layout;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.editorFactory.EditorFactory;
import com.gwtent.client.ui.model.Domain;

public interface LayoutCreator {
	public Widget generator(Domain domain, EditorFactory editorFactory);
}
