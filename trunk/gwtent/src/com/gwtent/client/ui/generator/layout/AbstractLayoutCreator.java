/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


package com.gwtent.client.ui.generator.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.editor.EditorEventAware;
import com.gwtent.client.ui.editorFactory.EditorFactory;
import com.gwtent.client.ui.model.Domain;


public abstract class AbstractLayoutCreator implements LayoutCreator {
	private List editorList = new ArrayList();
	
	protected abstract Widget doGenerateLayout(Domain domain, EditorFactory editorFactory);
	
	public Widget generator(Domain domain, EditorFactory editorFactory) {
		Widget result = doGenerateLayout(domain, editorFactory);
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
