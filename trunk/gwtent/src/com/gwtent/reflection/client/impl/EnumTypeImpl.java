package com.gwtent.reflection.client.impl;

import java.util.ArrayList;
import java.util.List;

import com.gwtent.reflection.client.EnumConstant;
import com.gwtent.reflection.client.EnumType;
import com.gwtent.reflection.client.Field;

public class EnumTypeImpl extends ClassTypeImpl implements EnumType {

	public EnumTypeImpl(PackageImpl declaringPackage,
			ClassTypeImpl enclosingType, boolean isLocalType, String name,
			boolean isInterface, boolean isDefaultInstantiable,
			Class<?> declaringClass) {
		super(declaringPackage, enclosingType, isLocalType, name, isInterface,
				isDefaultInstantiable, declaringClass);
	}
	
	public EnumTypeImpl(Class<?> declaringClass) {
		super(declaringClass);
	}

	public EnumType isEnum() {
		return this;
	}

	public EnumConstant[] getEnumConstants() {
		if (lazyEnumConstants == null) {
      List<EnumConstant> enumConstants = new ArrayList<EnumConstant>();
      for (Field field : getFields()) {
        if (field.isEnumConstant() != null) {
          enumConstants.add(field.isEnumConstant());
        }
      }

      lazyEnumConstants = enumConstants.toArray(new EnumConstant[0]);
    }

    return lazyEnumConstants;
	}
	
	private EnumConstant[] lazyEnumConstants;

}
