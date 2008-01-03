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
package com.gwtent.client.ui.model.value;

import java.util.HashMap;
import java.util.Map;

public class ValueRegister {
	private Map map = new HashMap();
	
	public void register(String typeName, ValueCreator valueCreator){
		map.put(typeName, valueCreator);
	}
	
	public ValueCreator getValueCreator(String typeName){
		return (ValueCreator)map.get(typeName);
	}
	
	private static ValueRegister valueRegister = null;
	
	public static ValueRegister getInstance(){
		if (valueRegister == null)
			valueRegister = new ValueRegister();
		
		return valueRegister;
	}
}
