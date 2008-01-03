package com.gwtent.client.ui.editorFactory;

import java.util.HashMap;
import java.util.Map;

import com.gwtent.client.ui.model.value.ValueRegister;

public class EditorRegister {
	private Map map = new HashMap();
	
	public void register(String typeName, EditorCreator creator){
		map.put(typeName, creator);
	}
	
	public EditorCreator getEditorRegister(String typeName){
		return (EditorCreator)map.get(typeName);
	}
	
	private static EditorRegister valueRegister = null;
	
	public static EditorRegister getInstance(){
		if (valueRegister == null)
			valueRegister = new EditorRegister();
		
		return valueRegister;
	}
}
