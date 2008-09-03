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
