/*
 * GwtEnt - Gwt ent library.
 * 
 * Copyright (c) 2007, James Luo(JamesLuo.au@gmail.com)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.gwtent.client.reflection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractMethod implements HasMetaData {

	private boolean isVarArgs = false;

	private final HasMetaData metaData = new MetaData();


	private int modifierBits;
	private final String name;

	private final List params = new ArrayList();

	private final List thrownTypes = new ArrayList();

	private final List typeParams = new ArrayList();

	// Only the builder can construct
	AbstractMethod(String name) {
		this.name = name;
	}

	public void addMetaData(String tagName, String[] values) {
		metaData.addMetaData(tagName, values);
	}

	public void addModifierBits(int bits) {
		modifierBits |= bits;
	}

	public void addThrows(Type type) {
		thrownTypes.add(type);
	}

	public Parameter findParameter(String name) {
		Iterator iterator = params.iterator();
		Parameter param = null;
		while (iterator.hasNext()) {
			param = (Parameter) iterator.next();
			if (param.getName().equals(name)) {
				return param;
			}
		}
		return null;
	}

	public abstract ClassType getEnclosingType();

	public String[][] getMetaData(String tagName) {
		return metaData.getMetaData(tagName);
	}

	public String[] getMetaDataTags() {
		return metaData.getMetaDataTags();
	}

	public String getName() {
		return name;
	}

	public Parameter[] getParameters() {
		return (Parameter[]) params.toArray(TypeOracle.NO_JPARAMS);
	}

	public abstract String getReadableDeclaration();

	public Type[] getThrows() {
		return (Type[]) thrownTypes.toArray(TypeOracle.NO_JTYPES);
	}

	// public TypeParameter[] getTypeParameters() {
	// return typeParams.toArray(new TypeParameter[typeParams.size()]);
	// }

	public abstract Constructor isConstructor();

	public boolean isDefaultAccess() {
		return 0 == (modifierBits & (TypeOracle.MOD_PUBLIC
				| TypeOracle.MOD_PRIVATE | TypeOracle.MOD_PROTECTED));
	}

	public abstract Method isMethod();

	public boolean isPrivate() {
		return 0 != (modifierBits & TypeOracle.MOD_PRIVATE);
	}

	public boolean isProtected() {
		return 0 != (modifierBits & TypeOracle.MOD_PROTECTED);
	}

	public boolean isPublic() {
		return 0 != (modifierBits & TypeOracle.MOD_PUBLIC);
	}

	public boolean isVarArgs() {
		return isVarArgs;
	}

	public void setVarArgs() {
		isVarArgs = true;
	}

	protected int getModifierBits() {
		return modifierBits;
	}

	protected void toStringParamsAndThrows(StringBuffer sb) {
		sb.append("(");
		boolean needComma = false;
		for (int i = 0, c = params.size(); i < c; ++i) {
			Parameter param = (Parameter) params.get(i);
			if (needComma) {
				sb.append(", ");
			} else {
				needComma = true;
			}
			if (isVarArgs() && i == c - 1) {
				ArrayType arrayType = param.getType().isArray();
//				assert (arrayType != null);
				sb.append(arrayType.getComponentType()
						.getParameterizedQualifiedSourceName());
				sb.append("...");
			} else {
				sb
						.append(param.getType()
								.getParameterizedQualifiedSourceName());
			}
			sb.append(" ");
			sb.append(param.getName());
		}
		sb.append(")");

		if (!thrownTypes.isEmpty()) {
			sb.append(" throws ");
			needComma = false;
			Iterator iterator = thrownTypes.iterator();
			while (iterator.hasNext()) {
				Type thrownType = (Type) iterator.next();
				if (needComma) {
					sb.append(", ");
				} else {
					needComma = true;
				}
				sb.append(thrownType.getParameterizedQualifiedSourceName());
			}
		}
	}

	// protected void toStringTypeParams(StringBuilder sb) {
	// sb.append("<");
	// boolean needComma = false;
	// for (TypeParameter typeParam : typeParams) {
	// if (needComma) {
	// sb.append(", ");
	// } else {
	// needComma = true;
	// }
	// sb.append(typeParam.getName());
	// sb.append(typeParam.getBounds().toString());
	// }
	// sb.append(">");
	// }

	void addParameter(Parameter param) {
		params.add(param);
	}

	// void addTypeParameter(TypeParameter typeParameter) {
	// typeParams.add(typeParameter);
	// }

	boolean hasParamTypes(Type[] paramTypes) {
		if (params.size() != paramTypes.length) {
			return false;
		}

		for (int i = 0; i < paramTypes.length; i++) {
			Parameter candidate = (Parameter) params.get(i);
			// Identity tests are ok since identity is durable within an oracle.
			//
			if (candidate.getType() != paramTypes[i]) {
				return false;
			}
		}
		return true;
	}
	
	boolean hasParamTypesByTypeName(String[] paramTypes) {
		if (params.size() != paramTypes.length) {
			return false;
		}

		for (int i = 0; i < paramTypes.length; i++) {
			Parameter candidate = (Parameter) params.get(i);
			// Identity tests are ok since identity is durable within an oracle.
			//
			if (!candidate.getTypeName().equals(paramTypes[i])) {
				return false;
			}
		}
		return true;
	}
}
