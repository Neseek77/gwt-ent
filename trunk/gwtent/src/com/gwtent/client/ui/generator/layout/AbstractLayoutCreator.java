package com.gwtent.client.ui.generator.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.editor.EditorEventAware;
import com.gwtent.client.ui.editorFactory.EditorFactory;
import com.gwtent.client.ui.model.Fields;


public abstract class AbstractLayoutCreator implements LayoutCreator {
	private List editorList = new ArrayList();
	
	protected abstract Widget doGenerateLayout(Fields fields, EditorFactory editorFactory);
	
	public Widget generator(Fields fields, EditorFactory editorFactory) {
		Widget result = doGenerateLayout(fields, editorFactory);
		finishAddEditors();
		return result;
	}
	
	/**
	 * Important:
	 * SubClass should invoke this function when
	 * add new Editor to layout
	 * this may support EditorEventAware interface
	 * @param editor
	 */
	protected void addEditor(Editor editor){
		editorList.add(editor);
	}
	
	/**
	 * tell the editors, all editor add finished
	 * to support EditorEventAware interface
	 */
	protected void finishAddEditors(){
		Iterator iterator = editorList.iterator();
		while (iterator.hasNext()) {
			Editor editor = (Editor) iterator.next();
			
			if (editor instanceof EditorEventAware){
				((EditorEventAware)editor).finishAddEditors();
			}
		}
	}

}
