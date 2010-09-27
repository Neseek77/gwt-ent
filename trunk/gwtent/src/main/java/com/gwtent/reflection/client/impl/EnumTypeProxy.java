package com.gwtent.reflection.client.impl;

import com.gwtent.reflection.client.EnumConstant;
import com.gwtent.reflection.client.EnumType;


public class EnumTypeProxy<T> extends ClassTypeProxy<T> implements EnumType<T> {

	public EnumConstant[] getEnumConstants() {
		EnumType <?> t = (EnumType<?>) classType;
		return   t.getEnumConstants();
	}

 
	 
}
