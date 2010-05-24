package com.gwtent.uibinder.client.gxt.widget;

import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.EnumConstant;
import com.gwtent.client.reflection.EnumType;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;

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
