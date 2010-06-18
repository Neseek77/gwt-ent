package com.gwtent.uibinder.client.gxt.widget;

import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.EnumType;
import com.gwtent.reflection.client.ReflectionUtils;
import com.gwtent.reflection.client.TypeOracle;

public class EnumCombo<T extends Enum<T>> extends SimpleComboBox<T> {
	
	public EnumCombo(){
		this.setEditable(false);
	}

	public void updateComboSelectList(Class<T> clazzOfEnum){
		ReflectionUtils.checkReflection(clazzOfEnum);
		ClassType type = TypeOracle.Instance.getClassType(clazzOfEnum);
		
		EnumType eType = type.isEnum();
		if (eType == null)
			throw new RuntimeException("Class(" + clazzOfEnum.getName() + ") must a Enum type.");
		
		T[] constants = clazzOfEnum.getEnumConstants();
		if (constants == null) {
      throw new IllegalArgumentException(
      		clazzOfEnum.getName() + " is not an enum.");
    }
		
    for (T constant : constants) {
        this.add(constant);
    }
	}
	
}
