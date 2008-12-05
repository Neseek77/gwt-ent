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


package com.gwtent.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.gwtent.client.ui.model.value.ValueCreator;
import com.gwtent.client.ui.editorFactory.EditorCreator;

/**
 * Use ValueType and Editor together that ensure Editor
 * will get correct ValueType
 * 
 * @author James Luo
 * 2008-1-4
 *
 */
public class ValueEditorRegister {
	private Map map = new HashMap();
	
	public void register(String typeName, ValueCreator valueCreator, EditorCreator editorCreator){
		map.put(typeName, new ValueEditor(valueCreator, editorCreator));
	}
	
	public ValueCreator getValueCreator(String typeName){
		return ((ValueEditor)map.get(typeName)).valueCreator;
	}
	
	public EditorCreator getEditorCreator(String typeName){
		return ((ValueEditor)map.get(typeName)).editorCreator;
	}
	
	
	static class ValueEditor {
		ValueCreator valueCreator;
		EditorCreator editorCreator;
		
		ValueEditor(ValueCreator valueCreator, EditorCreator editorCreator){
			this.valueCreator = valueCreator;
			this.editorCreator = editorCreator;
		}
	}
	
	private static ValueEditorRegister valueEditorRegister = null;
	
	public static ValueEditorRegister getInstance(){
		if (valueEditorRegister == null)
			valueEditorRegister = new ValueEditorRegister();
		
		return valueEditorRegister;
	}
}
